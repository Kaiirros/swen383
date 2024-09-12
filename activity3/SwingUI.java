

import javax.swing.JFrame ;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout ;

import java.util.Observer ;
import java.util.Observable ;
import java.io.IOException;


/*
 * Unit Enum for layout display.
 */
enum Unit{
    KELVIN, CELCIUS, FAHRENHEIT, INCHES, MILLIBARS
}


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
        mainFrame.setLabel(Unit.KELVIN, station.getKelvin());
        mainFrame.setLabel(Unit.CELCIUS, station.getCelsius());
        mainFrame.setLabel(Unit.FAHRENHEIT, station.getFahrenheit());
        mainFrame.setLabel(Unit.INCHES, station.getPressureInches());
        mainFrame.setLabel(Unit.MILLIBARS, station.getPressureMillibars());

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
    private JLabel fahrenheitField ;
    private JLabel pressureInchesField ;
    private JLabel pressureMillibarsField ;

    public FrameConstructor() throws IOException{
        super("Weather Station (SwingUI)") ;
        /*
         * WeatherStation frame is a grid of 1 row by an indefinite
         * number of columns.
         */
        this.setLayout(new GridLayout(1,0, 20, 500)) ;
        this.setSize(1500, 500);

        /*
         * There are two panels, one each for Kelvin and Celsius, added to the
         * frame. Each Panel is a 2 row by 1 column grid, with the temperature
         * name in the first row and the temperature itself in the second row.
         */
        JPanel panel ;
        /*
         * Set up Kelvin display.
         */
        panel = new JPanel(new GridLayout(2,1)) ;
        this.add(panel) ;
        createLabel(" Kelvin ", panel, false, Color.blue) ;
        kelvinField = createLabel("0", panel, true, Color.blue) ;
        /*
         * Set up awesome images.
         */

        /*
         * Set up Celsius display.
         */
        panel = new JPanel(new GridLayout(2,1)) ;
        this.add(panel) ;
        createLabel(" Celsius ", panel, false, Color.green) ;
        celsiusField = createLabel("0", panel, true, Color.green) ;

        /*
         * Set up Fahrenheit display.
         */
        panel = new JPanel(new GridLayout(2,1)) ;
        this.add(panel) ;
        createLabel(" Fahrenheit ", panel, false, Color.cyan) ;
        fahrenheitField = createLabel("0", panel, true, Color.cyan) ;

        /*
         * Set up Inches display.
         */
        
        panel = new JPanel(new GridLayout(2,1)) ;
        this.add(panel) ;
        createLabel(" Inches ", panel, false, Color.magenta) ;
        pressureInchesField = createLabel("0", panel, true, Color.magenta) ;

        /*
         * Set up millibars display.
         */
        panel = new JPanel(new GridLayout(2,1)) ;
        this.add(panel) ;
        createLabel(" Millibars ", panel, false, Color.orange) ;
        pressureMillibarsField = createLabel("0", panel, true, Color.orange) ;


         /*
         * Set up the frame's default close operation pack its elements,
         * and make the frame visible.
         */
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ;
        this.pack();
        this.setVisible(true) ;
    }

    public void setLabel(Unit field, double value) {

        switch (field) {
            case KELVIN:
                kelvinField.setText(String.format("%6.2f", value)) ;
                break;

            case CELCIUS:
                celsiusField.setText(String.format("%6.2f", value)) ;
                break;

            case FAHRENHEIT:
                fahrenheitField.setText(String.format("%6.2f", value)) ;
                break;

            case INCHES:
                pressureInchesField.setText(String.format("%6.2f", value)) ;
                break;

            case MILLIBARS:
                pressureMillibarsField.setText(String.format("%6.2f", value)) ;
                break;

            default:
                break;
        }
    }

    private JLabel createLabel(String title, JPanel panel, Boolean isTitle, Color color) {
        JLabel label = new JLabel(title) ;
        label.setForeground(color);
        panel.setBackground(Color.gray);
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