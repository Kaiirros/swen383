
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame ;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout ;

import java.util.Observer ;
import java.util.Observable ;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@SuppressWarnings("deprecation")
public class SwingUI implements Observer{

    private final WeatherStation station ;
    private final FrameConstructor mainFrame ;

    public SwingUI(WeatherStation station) throws IOException{
        this.station = station;
        this.station.addObserver(this) ;

        this.mainFrame = new FrameConstructor(); // Frame for window made in the frame constructor

    }

    /*
     * Observer 
     */
    @Override
    public void update(Observable obs, Object ignore) {
        if( station != obs ) {
            return ;
        }
        System.out.println("Updated...");
        mainFrame.setCelsiusJLabel(station.getCelsius());
        mainFrame.setKelvinJLabel(station.getKelvin());
        
    }
    
    public static void main(String[] args) throws IOException {
        WeatherStation ws = new WeatherStation() ;
        Thread thread = new Thread(ws) ;
        new SwingUI(ws) ;

        thread.start() ;
    }

}

/**
 Frame Constructor for the base, SwingUI GUI <br><br>
 >The constructor class creates the window and layout of the Kelvin and Celsius text fields
 @return void

 */
class FrameConstructor extends JFrame {
    private JLabel celsiusField ;   // put current celsius reading here
    private JLabel kelvinField ;    // put current kelvin reading here
    private JLabel celciusDifferenceField ;     // Old and new celcius temperature difference
    private JLabel kelvinDifferenceField ;      // Old and new kelvin temperature difference
    private final BufferedImage coldImage = ImageIO.read(new File("./coldcat.jpg"));    // Image used for when the difference is -
    private final BufferedImage hotImage = ImageIO.read(new File("./hotcat.jpg"));  // Image used for when the difference is +
    private ImageIcon imageField = new ImageIcon();


    public FrameConstructor() throws IOException{
        super("Weather Station (SwingUI)") ;
        /*
         * WeatherStation frame is a grid of 1 row by an indefinite
         * number of columns.
         */
        this.setLayout(new GridLayout(1,0)) ;
        this.setSize(1500, 800);

        /*
         * There are two panels, one each for Kelvin and Celsius, added to the
         * frame. Each Panel is a 2 row by 1 column grid, with the temperature
         * name in the first row and the temperature itself in the second row.
         */
        JPanel panel ;

        /*
         * Set up Kelvin display.
         */
        panel = new JPanel(new GridLayout(3,1)) ;
        this.add(panel) ;
        createLabel(" Kelvin ", panel, false) ;
        kelvinField = createLabel("0", panel, true) ;
        kelvinDifferenceField = createLabel("-", panel, false) ;


        JLabel picLabel = new JLabel(imageField);
        this.add(picLabel);
        /*
         * Set up Celsius display.
         */
        panel = new JPanel(new GridLayout(3,1)) ;
        this.add(panel) ;
        createLabel(" Celsius ", panel, false) ;
        celsiusField = createLabel("0", panel, true) ;
        celciusDifferenceField = createLabel("-", panel, false) ;

        

         /*
         * Set up the frame's default close operation pack its elements,
         * and make the frame visible.
         */
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ;
        this.setVisible(true) ;
    }

    /*
     * Set the label holding the Kelvin temperature.
     */
    public void setKelvinJLabel(double temperature) {

        System.out.println(Double.parseDouble(kelvinField.getText()));
        double oldTemp = Double.parseDouble(kelvinField.getText());

        Color color = Color.BLACK;
        double difference = temperature - oldTemp;

        if (temperature > oldTemp){
            System.err.println("hotter");
            color = Color.RED;
            imageField.setImage(hotImage);
            repaint();
        } else {
            System.out.println("colder");
            color = Color.BLUE;
            imageField.setImage(coldImage);
            repaint();

        }
        kelvinField.setText(String.format("%6.2f", temperature)) ;
        kelvinDifferenceField.setText(String.format("%6.2f", difference));
        kelvinDifferenceField.setForeground(color);
    }

    /*
     * Set the label holding the Celsius temperature.
     */
    public void setCelsiusJLabel(double temperature) {
        System.out.println(Double.parseDouble(celsiusField.getText()));
        double oldTemp = Double.parseDouble(celsiusField.getText());

        Color color = Color.BLACK;
        double difference = temperature - oldTemp;

        if (temperature > oldTemp){
            System.err.println("hotter");
            color = Color.RED;
        } else {
            System.out.println("colder");
            color = Color.BLUE;
        }
        celsiusField.setText(String.format("%6.2f", temperature)) ;

        celciusDifferenceField.setText(String.format("%6.2f", difference));
        celciusDifferenceField.setForeground(color);
    }

    private JLabel createLabel(String title, JPanel panel, Boolean isTitle) {
        JLabel label = new JLabel(title) ;

        label.setHorizontalAlignment(JLabel.CENTER) ;
        label.setVerticalAlignment(JLabel.TOP) ;
        if (isTitle){
            label.setFont(new Font("Arial", Font.BOLD, 60));
        } else {
            label.setFont(new Font("monospace", Font.ITALIC, 40));
        }
        
        panel.add(label) ;

        return label ;
    }
}