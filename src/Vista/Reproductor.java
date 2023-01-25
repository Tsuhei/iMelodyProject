package Vista;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javazoom.jlgui.basicplayer.BasicPlayerException;

public class Reproductor extends javax.swing.JFrame {
    
    private lista list = new lista();
    private nodo actual = null;
    private Rplayer player;
    private Short x = 0;
    private DefaultListModel lista_modelo = new DefaultListModel();
    private String ultimaLista = "vacio";
    private boolean cambios = false;
    protected boolean detenido = false;

    public Reproductor() {
        setTitle("iMelody Reproductor");
        setResizable(false);
        Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/ImgReproductor/iMLogo-32x32.png"));
        setIconImage(icon);
        initComponents();
        setLocationRelativeTo(null);
        nombreCancion.setEditable(false);
        volumen.setEnabled(false);
        balance.setEnabled(false);
        
        playlist.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                JList lista = (JList) evt.getSource();
                if (evt.getClickCount() == 2) {
                    int index = lista.locationToIndex(evt.getPoint());
                    if (index != -1) {
                        actual = list.get_cancion(index);
                        x = 0;
                        playActionPerformed(null);
                    }
                }
            }
        });
                
                
        try {
            BufferedReader tec = new BufferedReader(new FileReader(System.getProperty("user.dir") + "\\config"));
            String aux = tec.readLine();
            if (aux.equals("Si")) {
                aux = tec.readLine();
                if (!aux.equals("vacio")) {
                    cargarLista(aux);
                }
            } else {
                cargarListaInicio.setSelected(false);
            }
        } catch (Exception e) {
        }

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent evt) {
                if (!list.IsEmpety() && cambios) {
                    int opcion = JOptionPane.showConfirmDialog(null, "¿Guardar cambios?");
                    if (opcion == JOptionPane.YES_OPTION) {
                        if (ultimaLista.equals("Vacío.")) {
                            ultimaLista = crearArchivoLista();
                        }
                        if (ultimaLista == null) {
                            ultimaLista = "Vacío.";
                        } else {
                            guardarLista(ultimaLista);
                        }
                    }
                }
                try {
                    BufferedWriter bw = new BufferedWriter(
                            new FileWriter(System.getProperty("user.dir") + "\\config"));
                    if (cargarListaInicio.isSelected()) {
                        bw.write("Si\r\n");
                        bw.write(ultimaLista + "\r\n");
                    } else {
                        bw.write("No\r\n");
                    }
                    bw.close();
                } catch (Exception e) {
                }
                System.exit(0);
            }
        });
        player = new Rplayer(this);
    }
    
     public void cargarLista(String ruta) {
        try {
            FileInputStream fis = new FileInputStream(new File(ruta));
            BufferedReader tec = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
            String input[];
            tec.readLine();

            while (tec.ready()) {
                input = tec.readLine().split("<");
                System.out.println(input[0] + " , " + input[1]);
                list.insertar(input[0], input[1]);
                lista_modelo.addElement(input[0]);
            }
            ultimaLista = ruta;
            cambios = false;
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error\nal cargar la lista.", "alerta", 1);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error.", "alerta", 1);
        }
        listaCanciones.setModel(lista_modelo);
    }

    public void guardarLista(String dir) {
        try {
            BufferedWriter tec = new BufferedWriter(new FileWriter(dir));
            tec.write("\r\n");

            nodo aux = list.first;
            while (aux != null) {
                tec.append(aux.nombre + "<" + aux.direccion + "\r\n");
                aux = aux.siguiente;
            }

            tec.close();
            cambios = false;
        } catch (Exception e) {
        }
    }

    public String crearArchivoLista() {
        String n = JOptionPane.showInputDialog("Escribe el nombre de la lista");
        if (n == null || n.isEmpty()) {
            return null;
        }
        
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int seleccion = chooser.showOpenDialog(this);
        File ruta;

        if (seleccion == JFileChooser.APPROVE_OPTION) {
            ruta = chooser.getSelectedFile();
        } else {
            return null;
        }
        File save = new File(ruta.getAbsolutePath() + "\\" + n + ".lis");
        if (save.exists()) {
            save.delete();
        }
        return save.getAbsolutePath();
    }

    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cubPestañas = new javax.swing.JPanel();
        repro = new javax.swing.JButton();
        ecuali = new javax.swing.JButton();
        reproEcua = new javax.swing.JTabbedPane();
        Reproductor = new javax.swing.JPanel();
        playlist = new javax.swing.JScrollPane();
        listaCanciones = new javax.swing.JList<>();
        agregar = new javax.swing.JButton();
        eliminar = new javax.swing.JButton();
        detener = new javax.swing.JButton();
        nombreCancion = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        tipo_reproduccion = new javax.swing.JComboBox<>();
        anterior = new javax.swing.JButton();
        play = new javax.swing.JButton();
        siguiente = new javax.swing.JButton();
        volumen = new javax.swing.JSlider();
        balance = new javax.swing.JSlider();
        txtVolumen = new javax.swing.JLabel();
        txtBalance = new javax.swing.JLabel();
        Ecualizador = new javax.swing.JPanel();
        slidereq = new javax.swing.JSlider();
        slidereq1 = new javax.swing.JSlider();
        slidereq2 = new javax.swing.JSlider();
        slidereq3 = new javax.swing.JSlider();
        slidereq4 = new javax.swing.JSlider();
        slidereq5 = new javax.swing.JSlider();
        slidereq6 = new javax.swing.JSlider();
        slidereq7 = new javax.swing.JSlider();
        slidereq8 = new javax.swing.JSlider();
        slidereq9 = new javax.swing.JSlider();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        Archivo = new javax.swing.JMenu();
        cargarListaInicio = new javax.swing.JCheckBoxMenuItem();
        guardar_lista = new javax.swing.JMenuItem();
        cargar_lista = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        Editar = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        cubPestañas.setBackground(new java.awt.Color(140, 82, 255));
        cubPestañas.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        repro.setBackground(new java.awt.Color(255, 255, 255));
        repro.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        repro.setForeground(new java.awt.Color(0, 0, 0));
        repro.setText("Reproductor");
        repro.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        repro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reproActionPerformed(evt);
            }
        });
        cubPestañas.add(repro, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, 120, -1));

        ecuali.setBackground(new java.awt.Color(255, 255, 255));
        ecuali.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        ecuali.setForeground(new java.awt.Color(0, 0, 0));
        ecuali.setText("Ecualizador");
        ecuali.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        ecuali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ecualiActionPerformed(evt);
            }
        });
        cubPestañas.add(ecuali, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 0, -1, -1));

        getContentPane().add(cubPestañas, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 610, 30));

        Reproductor.setBackground(new java.awt.Color(140, 82, 255));
        Reproductor.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        listaCanciones.setBackground(new java.awt.Color(255, 255, 255));
        listaCanciones.setFont(new java.awt.Font("MS UI Gothic", 0, 14)); // NOI18N
        listaCanciones.setForeground(new java.awt.Color(0, 0, 0));
        listaCanciones.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        playlist.setViewportView(listaCanciones);

        Reproductor.add(playlist, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, 150, 190));

        agregar.setBackground(new java.awt.Color(255, 255, 255));
        agregar.setFont(new java.awt.Font("MS UI Gothic", 1, 12)); // NOI18N
        agregar.setForeground(new java.awt.Color(0, 0, 0));
        agregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgReproductor/addsong.png"))); // NOI18N
        agregar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        agregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                agregarActionPerformed(evt);
            }
        });
        Reproductor.add(agregar, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 20, 40, 40));

        eliminar.setBackground(new java.awt.Color(255, 255, 255));
        eliminar.setFont(new java.awt.Font("MS UI Gothic", 1, 12)); // NOI18N
        eliminar.setForeground(new java.awt.Color(0, 0, 0));
        eliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgReproductor/deletesong.png"))); // NOI18N
        eliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        eliminar.setPreferredSize(new java.awt.Dimension(144, 30));
        eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminarActionPerformed(evt);
            }
        });
        Reproductor.add(eliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 70, 40, 40));

        detener.setBackground(new java.awt.Color(255, 255, 255));
        detener.setForeground(new java.awt.Color(0, 0, 0));
        detener.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgReproductor/stopsong.png"))); // NOI18N
        detener.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                detenerActionPerformed(evt);
            }
        });
        Reproductor.add(detener, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 70, 40, 40));

        nombreCancion.setBackground(new java.awt.Color(255, 255, 255));
        nombreCancion.setFont(new java.awt.Font("MS UI Gothic", 0, 24)); // NOI18N
        nombreCancion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nombreCancionActionPerformed(evt);
            }
        });
        Reproductor.add(nombreCancion, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 20, 340, 30));

        jLabel1.setFont(new java.awt.Font("MS UI Gothic", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Modo de Reproducción");
        Reproductor.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 150, 130, -1));

        tipo_reproduccion.setBackground(new java.awt.Color(255, 255, 255));
        tipo_reproduccion.setFont(new java.awt.Font("MS UI Gothic", 0, 12)); // NOI18N
        tipo_reproduccion.setForeground(new java.awt.Color(0, 0, 0));
        tipo_reproduccion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Normal", "Inversa", "Aleatorio" }));
        tipo_reproduccion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tipo_reproduccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tipo_reproduccionActionPerformed(evt);
            }
        });
        Reproductor.add(tipo_reproduccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 170, 130, -1));

        anterior.setBackground(new java.awt.Color(140, 82, 255));
        anterior.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgReproductor/anterior.png"))); // NOI18N
        anterior.setBorder(null);
        anterior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                anteriorActionPerformed(evt);
            }
        });
        Reproductor.add(anterior, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 70, 40, -1));

        play.setBackground(new java.awt.Color(140, 82, 255));
        play.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgReproductor/play.png"))); // NOI18N
        play.setBorder(null);
        play.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playActionPerformed(evt);
            }
        });
        Reproductor.add(play, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 70, -1, -1));

        siguiente.setBackground(new java.awt.Color(140, 82, 255));
        siguiente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgReproductor/siguiente.png"))); // NOI18N
        siguiente.setBorder(null);
        siguiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                siguienteActionPerformed(evt);
            }
        });
        Reproductor.add(siguiente, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 70, -1, -1));

        volumen.setBackground(new java.awt.Color(140, 82, 255));
        volumen.setForeground(new java.awt.Color(255, 255, 255));
        volumen.setValue(100);
        volumen.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        volumen.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                volumenStateChanged(evt);
            }
        });
        Reproductor.add(volumen, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 150, -1, -1));

        balance.setBackground(new java.awt.Color(140, 82, 255));
        balance.setForeground(new java.awt.Color(255, 255, 255));
        balance.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        balance.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                balanceStateChanged(evt);
            }
        });
        Reproductor.add(balance, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 190, -1, -1));

        txtVolumen.setFont(new java.awt.Font("MS UI Gothic", 1, 18)); // NOI18N
        txtVolumen.setForeground(new java.awt.Color(255, 255, 255));
        txtVolumen.setText("Volumen");
        Reproductor.add(txtVolumen, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 130, -1, -1));

        txtBalance.setFont(new java.awt.Font("MS UI Gothic", 1, 18)); // NOI18N
        txtBalance.setForeground(new java.awt.Color(255, 255, 255));
        txtBalance.setText("Balance");
        Reproductor.add(txtBalance, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 170, -1, -1));

        reproEcua.addTab("Repro", Reproductor);

        Ecualizador.setBackground(new java.awt.Color(140, 82, 255));

        slidereq.setBackground(new java.awt.Color(140, 82, 255));
        slidereq.setMinimum(-100);
        slidereq.setOrientation(javax.swing.JSlider.VERTICAL);
        slidereq.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        slidereq.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                slidereqStateChanged(evt);
            }
        });

        slidereq1.setBackground(new java.awt.Color(140, 82, 255));
        slidereq1.setMinimum(-100);
        slidereq1.setOrientation(javax.swing.JSlider.VERTICAL);
        slidereq1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        slidereq1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                slidereq1StateChanged(evt);
            }
        });

        slidereq2.setBackground(new java.awt.Color(140, 82, 255));
        slidereq2.setMinimum(-100);
        slidereq2.setOrientation(javax.swing.JSlider.VERTICAL);
        slidereq2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        slidereq2.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                slidereq2StateChanged(evt);
            }
        });

        slidereq3.setBackground(new java.awt.Color(140, 82, 255));
        slidereq3.setMinimum(-100);
        slidereq3.setOrientation(javax.swing.JSlider.VERTICAL);
        slidereq3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        slidereq3.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                slidereq3StateChanged(evt);
            }
        });

        slidereq4.setBackground(new java.awt.Color(140, 82, 255));
        slidereq4.setMinimum(-100);
        slidereq4.setOrientation(javax.swing.JSlider.VERTICAL);
        slidereq4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        slidereq4.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                slidereq4StateChanged(evt);
            }
        });

        slidereq5.setBackground(new java.awt.Color(140, 82, 255));
        slidereq5.setMinimum(-100);
        slidereq5.setOrientation(javax.swing.JSlider.VERTICAL);
        slidereq5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        slidereq5.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                slidereq5StateChanged(evt);
            }
        });

        slidereq6.setBackground(new java.awt.Color(140, 82, 255));
        slidereq6.setMinimum(-100);
        slidereq6.setOrientation(javax.swing.JSlider.VERTICAL);
        slidereq6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        slidereq6.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                slidereq6StateChanged(evt);
            }
        });

        slidereq7.setBackground(new java.awt.Color(140, 82, 255));
        slidereq7.setMinimum(-100);
        slidereq7.setOrientation(javax.swing.JSlider.VERTICAL);
        slidereq7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        slidereq7.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                slidereq7StateChanged(evt);
            }
        });

        slidereq8.setBackground(new java.awt.Color(140, 82, 255));
        slidereq8.setMinimum(-100);
        slidereq8.setOrientation(javax.swing.JSlider.VERTICAL);
        slidereq8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        slidereq8.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                slidereq8StateChanged(evt);
            }
        });

        slidereq9.setBackground(new java.awt.Color(140, 82, 255));
        slidereq9.setMinimum(-100);
        slidereq9.setOrientation(javax.swing.JSlider.VERTICAL);
        slidereq9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        slidereq9.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                slidereq9StateChanged(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("MS UI Gothic", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("31Hz");

        jLabel3.setFont(new java.awt.Font("MS UI Gothic", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("62");

        jLabel4.setFont(new java.awt.Font("MS UI Gothic", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("125");

        jLabel5.setFont(new java.awt.Font("MS UI Gothic", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("250");

        jLabel6.setFont(new java.awt.Font("MS UI Gothic", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("500");

        jLabel7.setFont(new java.awt.Font("MS UI Gothic", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("1KHz");

        jLabel8.setFont(new java.awt.Font("MS UI Gothic", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("2");

        jLabel9.setFont(new java.awt.Font("MS UI Gothic", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("4");

        jLabel10.setFont(new java.awt.Font("MS UI Gothic", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("8");

        jLabel11.setFont(new java.awt.Font("MS UI Gothic", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("16KHz");

        javax.swing.GroupLayout EcualizadorLayout = new javax.swing.GroupLayout(Ecualizador);
        Ecualizador.setLayout(EcualizadorLayout);
        EcualizadorLayout.setHorizontalGroup(
            EcualizadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EcualizadorLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(EcualizadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(slidereq, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(27, 27, 27)
                .addGroup(EcualizadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(slidereq1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(45, 45, 45)
                .addGroup(EcualizadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(slidereq2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(43, 43, 43)
                .addGroup(EcualizadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(slidereq3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(43, 43, 43)
                .addGroup(EcualizadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(slidereq4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(43, 43, 43)
                .addGroup(EcualizadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(EcualizadorLayout.createSequentialGroup()
                        .addComponent(slidereq5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)
                        .addComponent(slidereq6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(EcualizadorLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel8)))
                .addGap(45, 45, 45)
                .addGroup(EcualizadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(slidereq7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGroup(EcualizadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(EcualizadorLayout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addComponent(slidereq8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(EcualizadorLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel10)))
                .addGap(45, 45, 45)
                .addGroup(EcualizadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(slidereq9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        EcualizadorLayout.setVerticalGroup(
            EcualizadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EcualizadorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(EcualizadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(EcualizadorLayout.createSequentialGroup()
                        .addGroup(EcualizadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(slidereq1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(slidereq3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(slidereq5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(slidereq8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(slidereq9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(slidereq7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(slidereq6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(slidereq4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(slidereq2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(EcualizadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11)
                            .addGroup(EcualizadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel8)
                                .addComponent(jLabel7))
                            .addGroup(EcualizadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel6)
                                .addComponent(jLabel5))
                            .addComponent(jLabel4)
                            .addGroup(EcualizadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel3)
                                .addComponent(jLabel2)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(EcualizadorLayout.createSequentialGroup()
                        .addComponent(slidereq, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        reproEcua.addTab("Ecua", Ecualizador);

        getContentPane().add(reproEcua, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 0, 620, 270));

        jMenuBar1.setBackground(new java.awt.Color(255, 255, 255));

        Archivo.setBackground(new java.awt.Color(255, 255, 255));
        Archivo.setForeground(new java.awt.Color(0, 0, 0));
        Archivo.setText("Archivo");
        Archivo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Archivo.setFont(new java.awt.Font("MS UI Gothic", 0, 12)); // NOI18N

        cargarListaInicio.setBackground(new java.awt.Color(255, 255, 255));
        cargarListaInicio.setFont(new java.awt.Font("MS UI Gothic", 0, 12)); // NOI18N
        cargarListaInicio.setForeground(new java.awt.Color(0, 0, 0));
        cargarListaInicio.setSelected(true);
        cargarListaInicio.setText("Cargar  útima lista al abrir");
        cargarListaInicio.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cargarListaInicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cargarListaInicioActionPerformed(evt);
            }
        });
        Archivo.add(cargarListaInicio);

        guardar_lista.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        guardar_lista.setBackground(new java.awt.Color(255, 255, 255));
        guardar_lista.setFont(new java.awt.Font("MS UI Gothic", 0, 12)); // NOI18N
        guardar_lista.setForeground(new java.awt.Color(0, 0, 0));
        guardar_lista.setText("Guardar lista");
        guardar_lista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guardar_listaActionPerformed(evt);
            }
        });
        Archivo.add(guardar_lista);

        cargar_lista.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        cargar_lista.setBackground(new java.awt.Color(255, 255, 255));
        cargar_lista.setFont(new java.awt.Font("MS UI Gothic", 0, 12)); // NOI18N
        cargar_lista.setForeground(new java.awt.Color(0, 0, 0));
        cargar_lista.setText("Cargar lista");
        cargar_lista.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cargar_lista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cargar_listaActionPerformed(evt);
            }
        });
        Archivo.add(cargar_lista);

        jMenuItem1.setBackground(new java.awt.Color(255, 255, 255));
        jMenuItem1.setFont(new java.awt.Font("MS UI Gothic", 0, 12)); // NOI18N
        jMenuItem1.setForeground(new java.awt.Color(0, 0, 0));
        jMenuItem1.setText("Salir");
        jMenuItem1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        Archivo.add(jMenuItem1);

        jMenuBar1.add(Archivo);

        Editar.setBackground(new java.awt.Color(255, 255, 255));
        Editar.setForeground(new java.awt.Color(0, 0, 0));
        Editar.setText("Editar");
        Editar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Editar.setFont(new java.awt.Font("MS UI Gothic", 0, 14)); // NOI18N

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItem4.setBackground(new java.awt.Color(255, 255, 255));
        jMenuItem4.setFont(new java.awt.Font("MS UI Gothic", 0, 12)); // NOI18N
        jMenuItem4.setForeground(new java.awt.Color(0, 0, 0));
        jMenuItem4.setText("agregar cancion");
        jMenuItem4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        Editar.add(jMenuItem4);

        jMenuItem5.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItem5.setBackground(new java.awt.Color(255, 255, 255));
        jMenuItem5.setFont(new java.awt.Font("MS UI Gothic", 0, 12)); // NOI18N
        jMenuItem5.setForeground(new java.awt.Color(0, 0, 0));
        jMenuItem5.setText("eliminar cancion");
        jMenuItem5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        Editar.add(jMenuItem5);

        jMenuBar1.add(Editar);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void guardar_listaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardar_listaActionPerformed
        if (list.IsEmpety()) {
            JOptionPane.showMessageDialog(null, "No hay cancion(es).", "alerta", 1);
            return;
        }
        guardarLista(crearArchivoLista());
    }//GEN-LAST:event_guardar_listaActionPerformed

    private void cargar_listaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cargar_listaActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("archivo lis", "lis"));
        int seleccion = chooser.showOpenDialog(this);

        if (seleccion == JFileChooser.APPROVE_OPTION) {
            detenerActionPerformed(evt);
            list.clear();
            lista_modelo.clear();
            actual = list.first;

            String name = chooser.getSelectedFile().getName();
            if (name.length() < 4 || !name.substring(name.length() - 4, name.length()).equalsIgnoreCase(".lis")) {
                JOptionPane.showMessageDialog(null, "Este archivo no es una lista.", "alerta", 0);
                return;
            }
            cargarLista(chooser.getSelectedFile().getPath());
        }
    }//GEN-LAST:event_cargar_listaActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed

        
        Login VL = new Login();
        VL.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        agregarActionPerformed(evt);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        eliminarActionPerformed(evt);
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void playActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playActionPerformed

         detenido = true;
        if (list.IsEmpety()) {
            JOptionPane.showMessageDialog(null, "No hay canciones.", "alerta", 1);
        } else {
            if (actual == null) {
                actual = list.first;
            }
            try {
                if (x == 0) {
                    player.control.open(new URL("file:///" + actual.direccion));
                    player.control.play();
                    System.out.println("se inicia");
                    nombreCancion.setText(actual.nombre);
                    volumen.setEnabled(true);
                    balance.setEnabled(true);
                    x = 1;
                    play.setIcon(new ImageIcon(getClass().getResource("/ImgReproductor/pausa.png")));
                } else {
                    if (x == 1) {
                        player.control.pause();
                        System.out.println("se pausa!!!");
                        x = 2;
                        play.setIcon(new ImageIcon(getClass().getResource("/ImgReproductor/play.png")));
                    } else {
                        player.control.resume();
                        System.out.println("se continua!!!");
                        x = 1;
                        play.setIcon(new ImageIcon(getClass().getResource("/ImgReproductor/pausa.png")));
                    }
                }
            } catch (BasicPlayerException ex) {
                JOptionPane.showMessageDialog(null, "Error al abrir\nla canción.", "alerta", 1);
                x = 0;
            } catch (MalformedURLException ex) {
                Logger.getLogger(Reproductor.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Error al abrir la dirección\nde la canción.", "alerta", 1);
                x = 0;
            }
        }
        detenido = false;
    }//GEN-LAST:event_playActionPerformed

    private void agregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agregarActionPerformed

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Archivo MP3", "mp3", "mp3"));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setMultiSelectionEnabled(true);
        int seleccion = fileChooser.showOpenDialog(this);

        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File files[] = fileChooser.getSelectedFiles();
            boolean noMp3 = false, repetidos = false;
            cambios = true;

            for (File file : files) {
                String name = file.getName();
                if (name.length() < 4 || !name.substring(name.length() - 4, name.length()).equalsIgnoreCase(".mp3")) {
                    noMp3 = true;
                    continue;
                }
                if (list.buscar(file.getName(), file.getPath())) {
                    repetidos = true;
                    continue;
                }
                list.insertar(file.getName(), file.getPath());
                System.out.println(file.getName());
                System.out.println(file.getPath());
                lista_modelo.addElement(file.getName());
                listaCanciones.setModel(lista_modelo);
            }
            if (noMp3) {
                JOptionPane.showMessageDialog(null, "Se encontraron archivo(s) no mp3", "alerta", 0);
            }
            if (repetidos) {
                JOptionPane.showMessageDialog(null, "Se encontraron archivos repetidos", "alerta", 0);
            }
        }
    }//GEN-LAST:event_agregarActionPerformed

    private void detenerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_detenerActionPerformed

                detenido = true;
        play.setIcon(new ImageIcon(getClass().getResource("/ImgReproductor/play.png")));
        try {
            player.control.stop();
            x = 0;
           volumen.setEnabled(false);
           balance.setEnabled(false);
        } catch (BasicPlayerException ex) {
            Logger.getLogger(Reproductor.class.getName()).log(Level.SEVERE, null, ex);
        }
        detenido = false;
    }//GEN-LAST:event_detenerActionPerformed

    private void volumenStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_volumenStateChanged

          try {
            player.control.setGain((double) volumen.getValue() / 100);
        } catch (BasicPlayerException ex) {
            Logger.getLogger(Reproductor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_volumenStateChanged

    private void balanceStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_balanceStateChanged
        try {
            player.control.setPan((float) balance.getValue() / 100);
        } catch (BasicPlayerException ex) {
            Logger.getLogger( Reproductor.class.getName()).log(Level.SEVERE, null, ex);
        }        // TODO add your handling code here:
    }//GEN-LAST:event_balanceStateChanged

    private void slidereqStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_slidereqStateChanged

        player.eq[0] = (float) slidereq.getValue() / 100;
    }//GEN-LAST:event_slidereqStateChanged

    private void slidereq1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_slidereq1StateChanged

        player.eq[1] = (float) slidereq.getValue() / 100;
    }//GEN-LAST:event_slidereq1StateChanged

    private void slidereq2StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_slidereq2StateChanged

        player.eq[2] = (float) slidereq.getValue() / 100;
    }//GEN-LAST:event_slidereq2StateChanged

    private void slidereq3StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_slidereq3StateChanged

        player.eq[3] = (float) slidereq.getValue() / 100;
    }//GEN-LAST:event_slidereq3StateChanged

    private void slidereq4StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_slidereq4StateChanged

        player.eq[4] = (float) slidereq.getValue() / 100;
    }//GEN-LAST:event_slidereq4StateChanged

    private void slidereq5StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_slidereq5StateChanged

        player.eq[5] = (float) slidereq.getValue() / 100;
    }//GEN-LAST:event_slidereq5StateChanged

    private void slidereq6StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_slidereq6StateChanged

        player.eq[6] = (float) slidereq.getValue() / 100;
    }//GEN-LAST:event_slidereq6StateChanged

    private void slidereq7StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_slidereq7StateChanged

        player.eq[7] = (float) slidereq.getValue() / 100;
    }//GEN-LAST:event_slidereq7StateChanged

    private void slidereq8StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_slidereq8StateChanged

        player.eq[8] = (float) slidereq.getValue() / 100;
    }//GEN-LAST:event_slidereq8StateChanged

    private void slidereq9StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_slidereq9StateChanged

        player.eq[9] = (float) slidereq.getValue() / 100;
    }//GEN-LAST:event_slidereq9StateChanged

    
    protected void eventoSiguiente(){
        siguienteActionPerformed(null);
    }
    private void anteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_anteriorActionPerformed

            if (actual == null) {
            return;
        }

        switch (tipo_reproduccion.getSelectedIndex()) {
            case 0:
                if (actual.anterior == null) {
                    return;
                }
                actual = actual.anterior;
                break;

            case 1:
                if (actual.siguiente == null) {
                    return;
                }
                actual = actual.siguiente;
                break;

            default:
                int index = (int) (Math.random() * list.tam);
                actual = list.get_cancion(index);
                break;
        }

        x = 0;
        playActionPerformed(evt);
    }//GEN-LAST:event_anteriorActionPerformed

    private void siguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_siguienteActionPerformed
        if (actual == null) {
            return;
        }

        switch (tipo_reproduccion.getSelectedIndex()) {
            case 0:
                if (actual.siguiente == null) {
                    return;
                }
                actual = actual.siguiente;
                break;

            case 1:
                if (actual.anterior == null) {
                    return;
                }
                actual = actual.anterior;
                break;

            default:
                int index = (int) (Math.random() * list.tam);
                actual = list.get_cancion(index);
                break;
        }

        x = 0;
        playActionPerformed(evt);
    }//GEN-LAST:event_siguienteActionPerformed

    private void eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarActionPerformed

                if (list.IsEmpety()) {
            return;
        }
        int q = list.index(actual);
        if (q == -1) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un\nerror inesperado.", "alerta", 1);
        } else {
            lista_modelo.remove(q);
            list.borrar(actual);
            detenerActionPerformed(evt);
            if (list.IsEmpety()) {
                actual = null;
                nombreCancion.setText("...");
            } else {
                if (list.tam == 1) {
                    actual = list.first;
                } else {
                    if (actual.siguiente == null) {
                        actual = actual.anterior;
                    } else {
                        actual = actual.siguiente;
                    }
                }
                nombreCancion.setText(actual.nombre);
            }
        }
        cambios = true;                                 
    }//GEN-LAST:event_eliminarActionPerformed

    private void nombreCancionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nombreCancionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nombreCancionActionPerformed

    private void tipo_reproduccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tipo_reproduccionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tipo_reproduccionActionPerformed

    private void cargarListaInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cargarListaInicioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cargarListaInicioActionPerformed

    private void reproActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reproActionPerformed

        reproEcua.setSelectedIndex(0);
    }//GEN-LAST:event_reproActionPerformed

    private void ecualiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ecualiActionPerformed

                reproEcua.setSelectedIndex(1);
    }//GEN-LAST:event_ecualiActionPerformed

         public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Reproductor().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu Archivo;
    private javax.swing.JPanel Ecualizador;
    private javax.swing.JMenu Editar;
    private javax.swing.JPanel Reproductor;
    private javax.swing.JButton agregar;
    private javax.swing.JButton anterior;
    private javax.swing.JSlider balance;
    private javax.swing.JCheckBoxMenuItem cargarListaInicio;
    private javax.swing.JMenuItem cargar_lista;
    private javax.swing.JPanel cubPestañas;
    private javax.swing.JButton detener;
    private javax.swing.JButton ecuali;
    private javax.swing.JButton eliminar;
    private javax.swing.JMenuItem guardar_lista;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JList<String> listaCanciones;
    private javax.swing.JTextField nombreCancion;
    private javax.swing.JButton play;
    private javax.swing.JScrollPane playlist;
    private javax.swing.JButton repro;
    private javax.swing.JTabbedPane reproEcua;
    private javax.swing.JButton siguiente;
    private javax.swing.JSlider slidereq;
    private javax.swing.JSlider slidereq1;
    private javax.swing.JSlider slidereq2;
    private javax.swing.JSlider slidereq3;
    private javax.swing.JSlider slidereq4;
    private javax.swing.JSlider slidereq5;
    private javax.swing.JSlider slidereq6;
    private javax.swing.JSlider slidereq7;
    private javax.swing.JSlider slidereq8;
    private javax.swing.JSlider slidereq9;
    private javax.swing.JComboBox<String> tipo_reproduccion;
    private javax.swing.JLabel txtBalance;
    private javax.swing.JLabel txtVolumen;
    private javax.swing.JSlider volumen;
    // End of variables declaration//GEN-END:variables
}
