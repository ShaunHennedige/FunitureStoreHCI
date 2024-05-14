package raven.application.form.other;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class DoubleBed extends JPanel implements GLEventListener {

    private GLU glu;               // OpenGL utility library
    private GLUT glut;             // OpenGL utility toolkit
    private float rotateX, rotateY; // Rotation angles for the bed
    private int lastX, lastY;       // Last mouse coordinates
    private final float[] bedColor = {0.54f, 0.27f, 0.07f}; // Bed color (brown)
    private boolean isDaytime = true; // Day/Night toggle
    private final boolean isDragging = true; // Flag to track mouse dragging

    // Constructor
    public DoubleBed() {
        // Create OpenGL context and canvas
        GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);
        GLCanvas canvas = new GLCanvas(capabilities);
        canvas.addGLEventListener(this);

        // Add mouse motion listener for rotation
        canvas.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (isDragging) {
                    int dx = e.getX() - lastX;
                    int dy = e.getY() - lastY;
                    rotateX += dy;
                    rotateY += dx;
                    lastX = e.getX();
                    lastY = e.getY();
                    canvas.repaint();
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                lastX = e.getX();
                lastY = e.getY();
            }
        });

        // Create RGB color controllers
        JPanel controlPanel = new JPanel();
        JSlider redSlider = new JSlider(0, 255, (int) (bedColor[0] * 255));
        redSlider.addChangeListener(e -> {
            bedColor[0] = redSlider.getValue() / 255f;
            canvas.repaint();
        });
        controlPanel.add(new JLabel("R:"));
        controlPanel.add(redSlider);

        JSlider greenSlider = new JSlider(0, 255, (int) (bedColor[1] * 255));
        greenSlider.addChangeListener(e -> {
            bedColor[1] = greenSlider.getValue() / 255f;
            canvas.repaint();
        });
        controlPanel.add(new JLabel("G:"));
        controlPanel.add(greenSlider);

        JSlider blueSlider = new JSlider(0, 255, (int) (bedColor[2] * 255));
        blueSlider.addChangeListener(e -> {
            bedColor[2] = blueSlider.getValue() / 255f;
            canvas.repaint();
        });
        controlPanel.add(new JLabel("B:"));
        controlPanel.add(blueSlider);

        // Create button to toggle background color
        JButton backgroundButton = new JButton("Day/Night");
        backgroundButton.addActionListener(e -> {
            isDaytime = !isDaytime;
            canvas.repaint();
        });
        controlPanel.add(backgroundButton);
        // Create a button to save the design
        JButton saveButton = new JButton("Save PNG");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Prompt the user to choose a location to save the file
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showSaveDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    String filePath = selectedFile.getAbsolutePath();

                    // Create a buffered image to store the contents of the GLCanvas
                    BufferedImage image = new BufferedImage(canvas.getWidth(), canvas.getHeight(), BufferedImage.TYPE_INT_RGB);
                    Graphics2D graphics = image.createGraphics();
                    canvas.paint(graphics);
                    graphics.dispose();

                    // Save the buffered image as a PNG file
                    try {
                        ImageIO.write(image, "PNG", new File(filePath + ".png"));
                        JOptionPane.showMessageDialog(null, "Design saved successfully!");
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Error saving design: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        controlPanel.add(saveButton);
        // Create a button to delete the design
        JButton deleteButton = new JButton("Delete Design");
        deleteButton.addActionListener(e -> {
            rotateX = 0;
            rotateY = 0;
            isDaytime = true;
            canvas.repaint();
        });

        controlPanel.add(deleteButton);

        // Add canvas and control panel to the JPanel
        setLayout(new BorderLayout());
        add(canvas, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.NORTH);

        // Set panel size and make it visible
        setPreferredSize(new Dimension(1200, 1000));
        setVisible(true);
        canvas.requestFocusInWindow();
    }

    // Main method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("DoubleBed");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            frame.add(new DoubleBed());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    // Initialize OpenGL settings
    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        glu = new GLU();
        glut = new GLUT();
        gl.glClearColor(0.96f, 0.96f, 0.86f, 1.0f); // Beige background color
        gl.glEnable(GL2.GL_DEPTH_TEST); // Enable depth testing for 3D rendering
        gl.glEnable(GL2.GL_LIGHTING);    // Enable lighting
        gl.glEnable(GL2.GL_LIGHT0);      // Enable light source 0
        float[] lightPos = {0.0f, 5.0f, 0.0f, 1.0f}; // Light position
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, lightPos, 0);
        rotateX = rotateY = 0; // Initialize rotation angles
    }

    // Unused method for cleaning up resources
    @Override
    public void dispose(GLAutoDrawable drawable) {
        // Do nothing here
    }

    // Render the scene
    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        if (isDaytime) {
            gl.glClearColor(0.96f, 0.96f, 0.86f, 1.0f); // Day background color (beige)
        } else {
            gl.glClearColor(0.0f, 0.0f, 0.1f, 1.0f); // Night background color (dark blue)
        }
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT); // Clear buffers
        gl.glLoadIdentity(); // Load identity matrix

        gl.glTranslatef(0, 0, -5); // Move the scene back
        gl.glRotatef(rotateX, 1, 0, 0); // Rotate around X axis
        gl.glRotatef(rotateY, 0, 1, 0); // Rotate around Y axis

        // Set bed color
        gl.glColor3fv(bedColor, 0);

        // Draw the bed
        drawBed(gl);

        gl.glFlush(); // Flush the OpenGL pipeline
    }

    // Handle window resizing
    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        double aspect = (double) width / height;
        glu.gluPerspective(45.0, aspect, 0.1, 100.0); // Set perspective projection
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
        glu.gluLookAt(0.0, 0.0, 5.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0); // Set camera position
    }

    // Draw the bed using OpenGL primitives
    private void drawBed(GL2 gl) {
        float lowestY = -1.5f; // Initialize with the lowest point of the bed legs

        // Bed base (wood color)
        gl.glPushMatrix();
        gl.glTranslatef(0.0f, -0.5f, 0.0f);
        gl.glScalef(1.6f, 0.1f, 1.6f);
        glut.glutSolidCube(1);
        gl.glPopMatrix();

        // Bed headboard (wood color)
        gl.glPushMatrix();
        gl.glTranslatef(0.0f, -0.2f, -0.8f);
        gl.glScalef(1.6f, 0.6f, 0.1f);
        glut.glutSolidCube(1);
        gl.glPopMatrix();

        // Bed frame (wood color)
        gl.glColor3f(139/255f, 69/255f, 19/255f); // Brown color for the frame
        gl.glPushMatrix();
        gl.glTranslatef(-0.8f, -0.5f, 0.0f);
        gl.glScalef(0.1f, 0.4f, 1.6f);
        glut.glutSolidCube(1);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glTranslatef(0.8f, -0.5f, 0.0f);
        gl.glScalef(0.1f, 0.4f, 1.6f);
        glut.glutSolidCube(1);
        glut.glutSolidCube(1);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glTranslatef(0.0f, -0.5f, 0.8f);
        gl.glScalef(1.6f, 0.4f, 0.1f);
        glut.glutSolidCube(1);
        gl.glPopMatrix();

        // Floor
        gl.glColor3f(1.0f, 1.0f, 1.0f);; // White color
        gl.glPushMatrix();
        gl.glTranslatef(0.0f, (float) (lowestY - -0.75f), 0.0f); // Position the floor at the lowestY point
        gl.glScalef(2.5f, 0.1f, 4.0f); // Make the floor larger
        glut.glutSolidCube(1);
        gl.glPopMatrix();

        if (isDaytime) {
            gl.glClearColor(0.96f, 0.96f, 0.86f, 1.0f); // Beige background color
        } else {
            gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f); // Black background color
        }
    }

}
