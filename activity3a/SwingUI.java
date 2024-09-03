
import javax.swing.JFrame ;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Font;
import java.awt.GridLayout ;

import java.util.Observer ;
import java.util.Observable ;

public class SwingUI implements Observer{

    private final WeatherStation station ;
    private final KelvinTempSensor sensor ; // Temperature sensor.
    private final FrameConstructor mainFrame ;

    public SwingUI(WeatherStation station){
        this.station = station;
        this.station.addObserver(this) ;

        this.sensor = new KelvinTempSensor();
        this.mainFrame = new FrameConstructor();

    }

    /*
     * Observer 
     */
    public void update(Observable obs, Object ignore) {
        if( station != obs ) {
            return ;
        }
        System.out.println("Updated...");
        mainFrame.setCelsiusJLabel(station.getCelsius());
        mainFrame.setKelvinJLabel(station.getKelvin());
        
    }
    
    public static void main(String[] args) {
        WeatherStation ws = new WeatherStation() ;
        Thread thread = new Thread(ws) ;
        SwingUI ui = new SwingUI(ws) ;

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

    public FrameConstructor(){
        super("Weather Station (SwingUI)") ;
        /*
         * WeatherStation frame is a grid of 1 row by an indefinite
         * number of columns.
         */
        this.setLayout(new GridLayout(1,0)) ;

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
        createLabel(" Kelvin ", panel, false) ;
        kelvinField = createLabel("Loading...", panel, true) ;

        /*
         * Set up Celsius display.
         */
        panel = new JPanel(new GridLayout(2,1)) ;
        this.add(panel) ;
        createLabel(" Celsius ", panel, false) ;
        celsiusField = createLabel("Loading...", panel, true) ;

         /*
         * Set up the frame's default close operation pack its elements,
         * and make the frame visible.
         */
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ;
        this.pack() ;
        this.setVisible(true) ;
    }

    /*
     * Set the label holding the Kelvin temperature.
     */
    public void setKelvinJLabel(double temperature) {
        kelvinField.setText(String.format("%6.2f", temperature)) ;
    }

    /*
     * Set the label holding the Celsius temperature.
     */
    public void setCelsiusJLabel(double temperature) {
        celsiusField.setText(String.format("%6.2f", temperature)) ;
    }

    private JLabel createLabel(String title, JPanel panel, Boolean isTitle) {
        JLabel label = new JLabel(title) ;

        label.setHorizontalAlignment(JLabel.CENTER) ;
        label.setVerticalAlignment(JLabel.TOP) ;
        if (isTitle){
            label.setFont(new Font("monospace", Font.BOLD, 60));
        } else {
            label.setFont(new Font("monospace", ABORT, 40));
        }
        
        panel.add(label) ;

        return label ;
    }
}