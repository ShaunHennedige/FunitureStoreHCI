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

public class Couch extends JPanel implements GLEventListener {

    private GLU glu;               // OpenGL utility library
    private GLUT glut;             // OpenGL utility toolkit
    private float rotateX, rotateY; // Rotation angles for the couch
    private int lastX, lastY;       // Last mouse coordinates
    private final float[] couchColor = {0.5f, 0.5f, 0.5f}; // Default couch color (gray)
    private float stoolColorR = 0.55f; // Initial stool color (R component)
    private float stoolColorG = 0.27f; // Initial stool color (G component)
    private float stoolColorB = 0.07f; // Initial stool color (B component)
    private boolean isDaytime = true; // Day/Night toggle

    // Constructor
    public Couch() {
        // Create OpenGL context and canvas
        GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);
        GLCanvas canvas = new GLCanvas(capabilities);
        canvas.addGLEventListener(this);

        // Add mouse motion listener for rotation
        canvas.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int dx = e.getX() - lastX;
                int dy = e.getY() - lastY;
                rotateX += dy;
                rotateY += dx;
                lastX = e.getX();
                lastY = e.getY();
                canvas.repaint();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                lastX = e.getX();
                lastY = e.getY();
            }
        });

        Panel colorPanel = new Panel(); // Create a panel for color sliders
        JSlider redSlider = new JSlider(0, 255, (int) (stoolColorR * 255));
        redSlider.addChangeListener(e -> {
            stoolColorR = redSlider.getValue() / 255f;
            canvas.repaint();
        });
        colorPanel.add(new JLabel("R:"));
        colorPanel.add(redSlider);

        JSlider greenSlider = new JSlider(0, 255, (int) (stoolColorG * 255));
        greenSlider.addChangeListener(e -> {
            stoolColorG = greenSlider.getValue() / 255f;
            canvas.repaint();
        });
        colorPanel.add(new JLabel("G:"));
        colorPanel.add(greenSlider);

        JSlider blueSlider = new JSlider(0, 255, (int) (stoolColorB * 255));
        blueSlider.addChangeListener(e -> {
            stoolColorB = blueSlider.getValue() / 255f;
            canvas.repaint();
        });
        colorPanel.add(new JLabel("B:"));
        colorPanel.add(blueSlider);

        // Create button to toggle background color
        JButton backgroundButton = new JButton("Day/Night");
        backgroundButton.addActionListener(e -> {
            isDaytime = !isDaytime;
            canvas.repaint();
        });
        colorPanel.add(backgroundButton);

        // Add canvas and color panel to the panel
        setLayout(new BorderLayout());

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
        colorPanel.add(saveButton);
        // Create a button to delete the design
        JButton deleteButton = new JButton("Delete Design");
        deleteButton.addActionListener(e -> {
            rotateX = 0;
            rotateY = 0;
            isDaytime = true;
            canvas.repaint();
        });

        colorPanel.add(deleteButton);
        setLayout(new BorderLayout());
        add(colorPanel, BorderLayout.NORTH);
        add(canvas, BorderLayout.CENTER);

        // Set panel size and make it visible
        setPreferredSize(new Dimension(1200, 1000));
        setVisible(true);

        canvas.requestFocusInWindow();
    }

    // Main method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Couch");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            frame.add(new Couch());
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
            float[] lightPos = {0.0f, 5.0f, 0.0f, 1.0f}; // Daytime light position
            gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, lightPos, 0);
        } else {
            gl.glClearColor(0.0f, 0.0f, 0.1f, 1.0f); // Night background color (dark blue)
            float[] lightPos = {0.0f, 0.0f, 5.0f, 1.0f}; // Nighttime light position
            gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, lightPos, 0);
        }
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT); // Clear buffers
        gl.glLoadIdentity(); // Load identity matrix

        gl.glTranslatef(0, 0, -5); // Move the scene back
        gl.glRotatef(rotateX, 1, 0, 0); // Rotate around X axis
        gl.glRotatef(rotateY, 0, 1, 0); // Rotate around Y axis

        // Set material properties for shading
        float[] matAmbient = {0.7f, 0.7f, 0.7f, 1.0f};
        float[] matDiffuse = {0.7f, 0.7f, 0.7f, 1.0f};
        float[] matSpecular = {1.0f, 1.0f, 1.0f, 1.0f};
        float[] matShininess = {100.0f};
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT, matAmbient, 0);
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, matDiffuse, 0);
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, matSpecular, 0);
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SHININESS, matShininess, 0);

        // Draw the couch with specified color
        drawCouch(gl);

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

    // Draw the couch using OpenGL primitives
    private void drawCouch(GL2 gl) {
        // Couch base
        gl.glColor3fv(couchColor, 0); // Set couch color
        gl.glPushMatrix();
        gl.glTranslatef(0.0f, -0.45f, 0.0f);
        gl.glScalef(2.0f, 0.05f, 1.0f);
        glut.glutSolidCube(1);
        gl.glPopMatrix();

        // Couch back
        gl.glPushMatrix();
        gl.glTranslatef(0.0f, -0.2f, -0.5f);
        gl.glScalef(2.0f, 0.6f, 0.1f);
        glut.glutSolidCube(1);
        gl.glPopMatrix();

        // Couch armrests
        gl.glPushMatrix();
        gl.glTranslatef(-0.9f, -0.3f, 0.0f);
        gl.glScalef(0.1f, 0.4f, 1.0f);
        glut.glutSolidCube(1);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glTranslatef(0.9f, -0.3f, 0.0f);
        gl.glScalef(0.1f, 0.4f, 1.0f);
        glut.glutSolidCube(1);
        gl.glPopMatrix();

        // Floor
        gl.glColor3f(1.0f, 1.0f, 1.0f);; // White color
        gl.glPushMatrix();
        gl.glTranslatef(0.0f, -0.6f, 0.0f);
        gl.glScalef(3.0f, 0.1f, 3.0f);
        glut.glutSolidCube(1);
        gl.glPopMatrix();
    }
}
