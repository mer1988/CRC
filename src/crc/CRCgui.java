
package crc;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * @authors Miguel Rodriguez
 *          Amparo Luna
 *          Rodrigo Zureck
 */
public class CRCgui extends JFrame{
    // declaracion de variables globales de Gui
    private JButton         PolGenerador, Codificar, Decodificar, generarMensaje;
    private JLabel          lblDecodificacion, lblMensaje;
    private JPanel          Panel1, Panel2, PBotones, PResultados;
    private JTextField      Mensaje, Decodificacion;
    private JTextArea       Proceso;
    private JScrollPane     JSP;
    // declaracion de variables globales de programa
    private String          Gxp, Gx;

    //constructor de Gui!!
    public CRCgui(){
        super("Codigo de Redundancia Ciclica");
        iniciarGUI();
        setSize(500,300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void iniciarGUI(){
        Panel1 = new JPanel(new BorderLayout(2, 2));

        PBotones = new JPanel(new FlowLayout());

        PolGenerador = new JButton("Cambiar G(x)");
        PolGenerador.addActionListener(new generador_handle());
        PBotones.add(PolGenerador);

        Codificar = new JButton("Codificar");
        Codificar.setEnabled(false);
        Codificar.addActionListener(new codificar_handler());
        PBotones.add(Codificar);

        Decodificar = new JButton("Decodificar");
        Decodificar.addActionListener(new decodificar_handler());
        Decodificar.setEnabled(false);
        PBotones.add(Decodificar);

        generarMensaje = new JButton("Generar Mensaje");
        generarMensaje.addActionListener(new generarCodigo_handle());
        generarMensaje.setEnabled(false);
        PBotones.add(generarMensaje);

        Panel1.add(PBotones, BorderLayout.NORTH);

        Panel2 = new JPanel(new BorderLayout(5, 5));

        PResultados = new JPanel(new GridLayout(2,2));

        lblMensaje = new JLabel("Mensaje para Codificar: ");
        PResultados.add(lblMensaje);

        lblDecodificacion = new JLabel("Mensaje Codificado: ");
        PResultados.add(lblDecodificacion);

        Mensaje = new JTextField();
        Mensaje.setEnabled(false);
        PResultados.add(Mensaje);
        
        Decodificacion = new JTextField();
        Decodificacion.setEnabled(false);
        PResultados.add(Decodificacion);
        
        Panel2.add(PResultados, BorderLayout.NORTH);

        Proceso = new JTextArea();
        Proceso.setEditable(false);
        JSP = new JScrollPane(Proceso);
        Panel2.add(JSP, BorderLayout.CENTER);

        Panel1.add(Panel2, BorderLayout.CENTER);

        add(Panel1);
    }
    private class generador_handle implements ActionListener{
        public void actionPerformed(ActionEvent e) {           
            Gxp = JOptionPane.showInputDialog(CRCgui.this, "Ingrese el polinomio Generador G(x): ");
            Gx = parser.convertirPol(Gxp);
            Decodificacion.setText("");
            Mensaje.setText("");
            if(Gxp != null){
                Codificar.setEnabled(true);
                Decodificar.setEnabled(true);
                generarMensaje.setEnabled(true);
                Mensaje.setEnabled(true);
                Decodificacion.setEnabled(true);
                Proceso.setText(Proceso.getText()+"Se cambio el polinomio generador a: "+Gxp+", que se representa: "+Gx+"\n");
            }
            else{
                JOptionPane.showMessageDialog(CRCgui.this, "Debe haber un G(x)", "Error!", 2, null);
                Codificar.setEnabled(false);
                Decodificar.setEnabled(false);
                generarMensaje.setEnabled(false);
                Mensaje.setEnabled(false);
                Decodificacion.setEnabled(false);
            }
        }
     }
     private class generarCodigo_handle implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            String input = JOptionPane.showInputDialog(CRCgui.this, "el mensaje a codificar: ");
            char x = ' ';
            String n = null;
            for (int i=0; i<input.length(); i++){
                x= input.charAt(i);
                n=Integer.toBinaryString(x);
                int l = n.length();
                for(int j=0;j<8-l;j++){
                    n = "0"+n;
                }
                Mensaje.setText(Mensaje.getText()+n);
            }
            Proceso.setText(Proceso.getText()+"Se Agrego un mensaje: "+input+", que se representa: "+ Mensaje.getText() +"en binario \n");
        }
     }
     private class codificar_handler implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            String r = operacionesBinarias.divisionBinariaConResto(Mensaje.getText(), Gx, Proceso);
            Decodificacion.setText(Mensaje.getText()+ r);
            Proceso.setText(Proceso.getText()+"Se codifico el mensaje: "+Mensaje.getText()+", con redundancia Ciclica: "+r+"\n");
        }
     }
     private class decodificar_handler implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            String r = operacionesBinarias.divisionBinariaConResto(Decodificacion.getText(), Gx , Proceso);
            if(r.replace("0", "").equals("")){
                //String ascii = binarioString();
                JOptionPane.showMessageDialog(CRCgui.this, "Decodificacion exitosa!");
                Proceso.setText(Proceso.getText()+"Se decodifico el mensaje: "+Decodificacion.getText()+"Exitosamente, el mensaje obtenido en ASCII fue: "+obteberString(Decodificacion.getText())+"\n");
            }
            else{
                JOptionPane.showMessageDialog(CRCgui.this, "Hubo errores en la transmision, para mas detalles mirar el log!");
                Proceso.setText(Proceso.getText()+"Se decodifico el mensaje: "+Decodificacion.getText()+"sin exito, el reciduo de dividir entre el generado fue: "+r+"\n");
            }
        }

        private String obteberString(String text) {
            String cadena = text.substring(0, text.length() - (Gx.length() - 1));
            String Resp = "";
            String cod = "";
            int cont = 0;            
            try{
                for(int i =0; i<=cadena.length();i++){                    
                    if(cont < 8){
                        cod += cadena.substring(i,i+1);
                        cont++;
                    }
                    else{
                        int x = Integer.parseInt(cod, 2);
                        Resp = Resp + (char) x;
                        cod = cadena.substring(i,i+1);
                        cont = 1;
                    }
                }
            }
            catch(Exception ex){

            }
            return Resp;
        }
     }

}
