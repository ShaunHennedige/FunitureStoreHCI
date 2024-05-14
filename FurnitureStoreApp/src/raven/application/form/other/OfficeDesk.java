package raven.application.form.other;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class OfficeDesk extends JPanel implements GLEventListener {

    private GLU glu;               // OpenGL utility library
    private GLUT glut;             // OpenGL utility toolkit
    private float rotateX, rotateY; // Rotation angles for the barstool
    private float stoolColorR = 0.55f; // Initial stool color (R component)
    private float stoolColorG = 0.27f; // Initial stool color (G component)
    private float stoolColorB = 0.07f; // Initial stool color (B component)
    private boolean isDaytime = true; // Flag to track daytime or nighttime
    private boolean isDragging = false; // Flag to track mouse dragging
    private int lastX, lastY;       // Last mouse coordinates

    public OfficeDesk() {
        GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);
        GLCanvas canvas = new GLCanvas(capabilities);
        canvas.addGLEventListener(this);

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

        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                isDragging = true;
                lastX = e.getX();
                lastY = e.getY();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                isDragging = false;
            }
        });

        JPanel colorPanel = new JPanel(); // Create a panel for color sliders
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

        add(canvas, BorderLayout.CENTER);
        add(colorPanel, BorderLayout.NORTH);
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        glu = new GLU();
        glut = new GLUT();
        gl.glClearColor(0.96f, 0.96f, 0.86f, 1.0f);
        gl.glEnable(GL2.GL_DEPTH_TEST);
        gl.glEnable(GL2.GL_LIGHTING);
        gl.glEnable(GL2.GL_LIGHT0);
        float[] lightPos = {0.0f, 5.0f, 0.0f, 1.0f};
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, lightPos, 0);
        rotateX = rotateY = 0;
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();

        gl.glTranslatef(0, 0, -5);
        gl.glRotatef(rotateX, 1, 0, 0);
        gl.glRotatef(rotateY, 0, 1, 0);

        drawDesk(gl);

        gl.glFlush();
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        double aspect = (double) width / height;
        glu.gluPerspective(45.0, aspect, 0.1, 100.0);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
        glu.gluLookAt(0.0, 0.0, 5.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0);
    }

    private void drawDesk(GL2 gl) {
        // Desk color
        gl.glColor3f(stoolColorR, stoolColorG, stoolColorB);
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT_AND_DIFFUSE, new float[]{stoolColorR, stoolColorG, stoolColorB}, 0);

        // Desk surface
        gl.glPushMatrix();
        gl.glTranslatef(0.0f, -0.5f, 0.0f);
        gl.glScalef(1.5f, 0.1f, 1.0f);
        glut.glutSolidCube(1);
        gl.glPopMatrix();

        // Legs
        for (float x : new float[]{-0.6f, 0.6f}) {
            for (float z : new float[]{-0.4f, 0.4f}) {
                gl.glPushMatrix();
                gl.glTranslatef(x, -1f, z);
                gl.glScalef(0.1f, 1.0f, 0.1f);
                glut.glutSolidCube(1);
                gl.glPopMatrix();
            }
        }

        // Floor
        gl.glColor3f(1.0f, 1.0f, 1.0f); // White color
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT_AND_DIFFUSE, new float[]{0.5f, 0.5f, 0.5f}, 0);
        gl.glPushMatrix();
        gl.glTranslatef(0.0f, -1.5f, 0.0f);
        gl.glScalef(3.0f, 0.1f, 2.0f);
        glut.glutSolidCube(1);
        gl.glPopMatrix();

        // Background color (daytime or nighttime)
        if (isDaytime) {
            gl.glClearColor(0.96f, 0.96f, 0.86f, 1.0f); // Sky blue color for daytime
        } else {
            gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f); // Black color for nighttime
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Office Desk");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1200, 1000);
            frame.setLocationRelativeTo(null);

            OfficeDesk panel = new OfficeDesk();
            frame.add(panel);

            frame.setVisible(true);
        });
    }
}
