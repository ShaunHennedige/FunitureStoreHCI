package raven.application.form.other;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BunkBed extends JPanel implements GLEventListener {

    private GLU glu;  // OpenGL utility library
    private GLUT glut;  // OpenGL utility toolkit
    private float rotateX, rotateY; // Rotation angles for the bed
    private int lastX, lastY; // Last mouse coordinates

    private JSlider redSlider, greenSlider, blueSlider;
    private JButton dayNightToggleButton;
    private boolean isDayMode = true;

    // Constructor
    public BunkBed() {
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

        // Add RGB sliders for color selection
        redSlider = new JSlider(0, 255, 139);
        redSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                canvas.repaint();
            }
        });
        greenSlider = new JSlider(0, 255, 69);
        greenSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                canvas.repaint();
            }
        });
        blueSlider = new JSlider(0, 255, 19);
        blueSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                canvas.repaint();
            }
        });

        // Add sliders and toggle button to the JPanel
        JPanel controlPanel = new JPanel();
        controlPanel.add(new JLabel("R:"));
        controlPanel.add(redSlider);
        controlPanel.add(new JLabel("G:"));
        controlPanel.add(greenSlider);
        controlPanel.add(new JLabel("B:"));
        controlPanel.add(blueSlider);
        // Add toggle button for day/night mode
        dayNightToggleButton = new JButton("Day/Night");
        dayNightToggleButton.addActionListener(e -> {
            isDayMode = !isDayMode;
            canvas.repaint();
        });
        controlPanel.add(dayNightToggleButton);

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
            isDayMode = true;
            canvas.repaint();
        });

        controlPanel.add(deleteButton);

        // Add control panel and canvas to the JPanel
        setLayout(new BorderLayout());
        add(controlPanel, BorderLayout.NORTH);
        add(canvas, BorderLayout.CENTER);

        // Initialize rotation angles
        rotateX = rotateY = 0;
    }

    // Initialize OpenGL settings
    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        glu = new GLU();
        glut = new GLUT();
        if (isDayMode) {
            gl.glClearColor(0.96f, 0.96f, 0.86f, 1.0f); // Beige background color for day
        } else {
            gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f); // Black background color for night
        }
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
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT); // Clear buffers
        gl.glLoadIdentity(); // Load identity matrix

        gl.glTranslatef(0, 0, -5); // Move the scene back
        gl.glRotatef(rotateX, 1, 0, 0); // Rotate around X axis
        gl.glRotatef(rotateY, 0, 1, 0); // Rotate around Y axis

        // Draw the bunk bed
        drawBunkBed(gl);

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

    // Draw the bunk bed using OpenGL primitives
    private void drawBunkBed(GL2 gl) {
        // Dimensions for the bunk bed
        float bedWidth = 1.5f;
        float bedHeight = 0.2f;
        float bedLength = 2.0f;
        float legThickness = 0.1f;

        // Draw lower bed
        drawBed(gl, -bedWidth / 2, -1.0f, 0.0f, bedWidth, bedHeight, bedLength, legThickness);

        // Draw upper bed
        drawBed(gl, -bedWidth / 2, 0.5f, 0.0f, bedWidth, bedHeight, bedLength, legThickness);

        // Draw wall
        float wallWidth = 0.1f;
        float wallHeight = 3f;
        float wallLength = 3f;
        drawWall(gl, -bedWidth / 1f - wallWidth, -0.1f, -wallLength / 20f, wallWidth, wallHeight, wallLength);
    }

    // Draw a bed with given parameters
    private void drawBed(GL2 gl, float x, float y, float z, float width, float height, float length, float legThickness) {
        // Get RGB values from sliders
        int red = redSlider.getValue();
        int green = greenSlider.getValue();
        int blue = blueSlider.getValue();

        // Draw bed frame
        Color frameColor = new Color(red, green, blue);
        drawCube(gl, x, y, z, width, height, length, frameColor);

        // Draw bed legs
        drawLeg(gl, x - width / 2 + legThickness / 3, y - height / 4, z - length / 2 + legThickness / 2, legThickness, height, legThickness, frameColor);
        drawLeg(gl, x + width / 2 - legThickness / 3, y - height / 4, z - length / 2 + legThickness / 2, legThickness, height, legThickness, frameColor);
        drawLeg(gl, x + width / 2 - legThickness / 3, y - height / 4, z + length / 2 - legThickness / 2, legThickness, height, legThickness, frameColor);
        drawLeg(gl, x - width / 2 + legThickness / 3, y - height / 4, z + length / 2 - legThickness / 2, legThickness, height, legThickness, frameColor);

        // Draw mattress
        Color mattressColor = isDayMode ? new Color(170, 132, 58) : Color.WHITE;
        drawCube(gl, x, y + height / 2, z, width - 0.1f, 0.1f, length - 0.1f, mattressColor);

    }

    // Draw a leg with given parameters
    private void drawLeg(GL2 gl, float x, float y, float z, float width, float height, float length, Color color) {
        drawCube(gl, x, y, z, width, height, length, color);
    }

    // Draw a cube with given parameters
    private void drawCube(GL2 gl, float x, float y, float z, float width, float height, float length, Color color) {
        float[] ambient = {color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, 1.0f};
        float[] diffuse = {(float) (color.getRed() / 255f * 0.8), (float) (color.getGreen() / 255f * 0.8), (float) (color.getBlue() / 255f * 0.8), 1.0f};
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT, ambient, 0);
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, diffuse, 0);

        gl.glPushMatrix();
        gl.glTranslatef(x, y, z);
        gl.glScalef(width, height, length);
        glut.glutSolidCube(1);
        gl.glPopMatrix();
    }

    private void drawWall(GL2 gl, float x, float y, float z, float width, float height, float length) {
        // Get RGB values from sliders
        int red = redSlider.getValue();
        int green = greenSlider.getValue();
        int blue = blueSlider.getValue();

        // Draw wall
        Color wallColor = new Color(red, green, blue);
        drawCube(gl, x, y, z, width, height, length, wallColor);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("BunkBed");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 1000);
        frame.setLocationRelativeTo(null);

        JPanel panel = new BunkBed();
        frame.add(panel);

        frame.setVisible(true);
    }
}
