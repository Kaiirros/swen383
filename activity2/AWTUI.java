/*
 * Initial Author
 *      Michael J. Lutz
 *
 * Other Contributers
 *
 * Acknowledgements
 */
 
/*
 * AWT UI class used for displaying the information from the
 * associated weather station object.
 * This is an extension of JFrame, the outermost container in
 * a AWT application.
 */

import java.awt.* ;
import java.awt.event.* ;

public class AWTUI extends Frame implements Runnable{
    public Label celsiusField ;   // put current celsius reading here
    public Label kelvinField ;    // put current kelvin reading here
    private final KelvinTempSensor sensor ; // Temperature sensor.
    private final long PERIOD = 1000 ;      // 1 sec = 1000 ms.

    /*
     * A Font object contains information on the font to be used to
     * render text.
     */
    private static Font labelFont =
        new Font(Font.SERIF, Font.PLAIN, 72) ;

    /*
     * Create and populate the AWTUI JFrame with panels and labels to
     * show the temperatures.
     */
    public AWTUI() {
        super("Weather Station (AWTUI)") ;
        sensor = new KelvinTempSensor() ;

        /*
         * WeatherStation frame is a grid of 1 row by an indefinite number
         * of columns.
         */
        setLayout(new GridLayout(1,0)) ;

        /*
         * There are two panels, one each for Kelvin and Celsius, added to the
         * frame. Each Panel is a 2 row by 1 column grid, with the temperature
         * name in the first row and the temperature itself in the second row.
         */

        /*
         * Set up Kelvin display.
         */
        Panel panel = new Panel(new GridLayout(2,1)) ;
        add(panel) ;
        setLabel(" Kelvin ", panel) ;
        kelvinField = setLabel("", panel) ;

        /*
         * Set up Celsius display.
         */
        panel = new Panel(new GridLayout(2,1)) ;
        add(panel) ;
        setLabel(" Celsius ", panel) ;
        celsiusField = setLabel("", panel) ;

        /*
         * Set up the window's default close operation and pack its elements.
         */
        addWindowListener(
                new WindowAdapter() {
                    public void windowClosing(WindowEvent windowEvent){
                        System.exit(0);
                    }        
                });

        /*
         * Pack the components in this frame and make the frame visible.
         */
        pack() ;
        setVisible(true) ;

        Thread thread = new Thread(this) ;
        thread.start();
    }

    public void setKelvinJLabel(double temperature) {
        kelvinField.setText(String.format("%6.2f", temperature)) ;
    }

    /*
     * Set the label holding the Celsius temperature.
     */
    public void setCelsiusJLabel(double temperature) {
        celsiusField.setText(String.format("%6.2f", temperature)) ;
    }

    public void run() {
        int reading ;           // actual sensor reading.
        double celsius ;        // sensor reading transformed to celsius
        double kelvin ;
        final int KTOC = -27315 ;   // Convert raw Kelvin reading to Celsius

        while( true ) {
            try {
                Thread.sleep(PERIOD) ;
            } catch (Exception e) {}    // ignore exceptions

            reading = sensor.reading() ;
            celsius = (reading + KTOC) / 100.0 ;
            kelvin = (reading / 100.0);
            /*
             * System.out.printf prints formatted data on the output screen.
             *
             * Most characters print as themselves.
             *
             * % introduces a format command that usually applies to the
             * next argument of printf:
             *   *  %6.2f formats the "celsius" (2nd) argument in a field
             *      at least 6 characters wide with 2 fractional digits.
             *   *  The %n at the end of the string forces a new line
             *      of output.
             *   *  %% represents a literal percent character.
             *
             * See docs.oracle.com/javase/tutorial/java/data/numberformat.html
             * for more information on formatting output.
             */
            //
            System.out.println("RUNNING");
            setCelsiusJLabel(celsius);
            setKelvinJLabel(kelvin);
            
        }
    }

    /*
     * Create a Label with the initial value <title>, place it in
     * the specified <panel>, and return a reference to the Label
     * in case the caller wants to remember it.
     */
    private Label setLabel(String title, Panel panel) {
        Label label = new Label(title) ;

        label.setAlignment(Label.CENTER) ;
        label.setFont(labelFont) ;
        panel.add(label) ;

        return label ;
    }
}
