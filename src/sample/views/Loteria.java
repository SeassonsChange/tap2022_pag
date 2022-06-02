package sample.views;

import java.util.Timer;
import java.util.TimerTask;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Loteria extends Stage implements EventHandler {
    private VBox vBox;
    private HBox hBox1;
    private HBox hBox2;
    private HBox pantalla;
    private Button btnAtras;
    private Button btnSiguiente;
    private Button btnJugar;
    private Label lblTiempo;
    private Label lblCartaFalt;
    private GridPane gdpPlantilla;
    private Image imgCarta;
    private Scene escena;
    private int n;
    private int vectoraux = 0;
    private Timer tmTiempo;
    private TimerTask tmtTarea;
    int cont = 0;
    int control = 0;
    Timeline animacion;
    private String[][] arImages2 = new String[][]{{"bandolon.jpg", "barril.jpeg", "botella.jpeg", "campana.jpg", "catrin.jpeg", "chalupa.jpg", "chavorruco.jpeg", "concha.jpeg", "corazon.jpg", "corona.jpg", "cotorro.jpg", "estrella.jpg", "graduada.jpeg", "luchador.jpeg", "maceta.jpeg", "negro.jpg"}, {"venado.jpeg", "valiente.jpg", "tambor.jpg", "tacos.jpeg", "soldado.jpg", "sandia.jpg", "rosa.jpeg", "rana.jpg", "pescado.jpg", "negro.jpg", "maceta.jpeg", "luchador.jpeg", "graduada.jpeg", "estrella.jpg", "cotorro.jpg", "corona.jpg"}, {"corazon.jpg", "concha.jpeg", "chavorruco.jpeg", "chalupa.jpg", "catrin.jpeg", "campana.jpg", "botella.jpeg", "barril.jpeg", "bandolon.jpg", "venado.jpeg", "valiente.jpg", "tambor.jpg", "tacos.jpeg", "soldado.jpg", "sandia.jpg", "rosa.jpeg"}, {"rana.jpg", "pescado.jpg", "negro.jpg", "maceta.jpeg", "luchador.jpeg", "graduada.jpeg", "estrella.jpg", "cotorro.jpg", "corona.jpg", "corazon.jpg", "concha.jpeg", "chavorruco.jpeg", "chalupa.jpg", "catrin.jpeg", "campana.jpg", "botella.jpeg"}, {"barril.jpeg", "bandolon.jpg", "venado.jpeg", "valiente.jpg", "tambor.jpg", "tacos.jpeg", "soldado.jpg", "sandia.jpg", "rosa.jpeg", "rana.jpg", "pescado.jpg", "negro.jpg", "maceta.jpeg", "luchador.jpeg", "graduada.jpeg", "estrella.jpg"}};
    private String[] arImages = new String[]{"bandolon.jpg", "barril.jpeg", "botella.jpeg", "campana.jpg", "catrin.jpeg", "chalupa.jpg", "chavorruco.jpeg", "concha.jpeg", "corazon.jpg", "corona.jpg", "cotorro.jpg", "estrella.jpg", "graduada.jpeg", "luchador.jpeg", "maceta.jpeg", "negro.jpg", "pescado.jpg", "rana.jpg", "rosa.jpeg", "sandia.jpg", "soldado.jpg", "tacos.jpeg", "tambor.jpg", "valiente.jpg", "venado.jpeg", " "};
    private Button[][] arBtnPlantilla = new Button[4][4];
    private Button btnCarta;
    private Image imgCartaP;
    private Image imgVolteada;
    private ImageView imv;
    private ImageView imgJ;
    private ImageView imvVolteada;
    private int CartasFaltantes = 16;
    int[] cartas;
    Boolean cerrar;

    public Loteria() {
        this.cartas = new int[this.arImages.length];
        this.cerrar = false;
        this.CrearUI();
        this.setTitle("Loteria");
        this.setScene(this.escena);
        this.show();
    }

    private void CrearUI() {
        this.btnAtras = new Button("Atras");
        this.btnAtras.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                if (Loteria.this.cont > 0) {
                    --Loteria.this.cont;
                    Loteria.this.CrearPlantilla(Loteria.this.cont);
                } else {
                    Loteria.this.cont = 4;
                    Loteria.this.CrearPlantilla(Loteria.this.cont);
                }

                System.out.println("Plantilla: " + Loteria.this.cont);
            }
        });
        this.btnAtras.setPrefWidth(100.0);
        this.btnSiguiente = new Button("Siguiente");
        this.btnSiguiente.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                if (Loteria.this.cont < 4) {
                    ++Loteria.this.cont;
                    Loteria.this.CrearPlantilla(Loteria.this.cont);
                } else {
                    Loteria.this.cont = 0;
                    Loteria.this.CrearPlantilla(Loteria.this.cont);
                }

                System.out.println("Plantila : " + Loteria.this.cont);
            }
        });
        this.btnSiguiente.setPrefWidth(100.0);
        this.lblTiempo = new Label("Tiempo:");
        this.lblCartaFalt = new Label();
        String cartaF = "";
        cartaF = cartaF + this.CartasFaltantes;
        this.lblCartaFalt.setText("Cartas Faltantes :" + cartaF);
        this.hBox1 = new HBox();
        this.hBox1.setSpacing(5.0);
        this.hBox1.getChildren().addAll(new Node[]{this.btnAtras, this.lblTiempo, this.btnSiguiente, this.lblCartaFalt});
        this.gdpPlantilla = new GridPane();
        this.PlatillaInicio();
        this.hBox2 = new HBox();
        this.pantalla = new HBox();
        this.btnCarta = new Button();
        this.imgVolteada = new Image("sample/images/Borrar.jpg");
        this.imvVolteada = new ImageView(this.imgVolteada);
        this.imvVolteada.setFitHeight(240.0);
        this.imvVolteada.setFitWidth(135.0);
        this.btnCarta.setGraphic(this.imvVolteada);
        this.btnCarta.setPrefWidth(135.0);
        this.btnCarta.setPrefHeight(240.0);
        this.pantalla.getChildren().add(this.btnCarta);
        this.pantalla.setAlignment(Pos.CENTER);
        this.hBox2.setSpacing(8.0);
        this.hBox2.getChildren().addAll(new Node[]{this.gdpPlantilla, this.pantalla});
        this.btnJugar = new Button("Jugar");
        this.btnJugar.addEventHandler(MouseEvent.MOUSE_CLICKED, this);
        this.btnJugar.setPrefWidth(368.0);
        this.arBtnPlantilla[0][0].setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Loteria.this.control(Loteria.this.n, 0, 0);
            }
        });
        this.arBtnPlantilla[0][1].setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Loteria.this.control(Loteria.this.n, 0, 1);
            }
        });
        this.arBtnPlantilla[0][2].setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Loteria.this.control(Loteria.this.n, 0, 2);
            }
        });
        this.arBtnPlantilla[0][3].setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Loteria.this.control(Loteria.this.n, 0, 3);
            }
        });
        this.arBtnPlantilla[1][0].setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Loteria.this.control(Loteria.this.n, 1, 0);
            }
        });
        this.arBtnPlantilla[1][1].setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Loteria.this.control(Loteria.this.n, 1, 1);
            }
        });
        this.arBtnPlantilla[1][2].setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Loteria.this.control(Loteria.this.n, 1, 2);
            }
        });
        this.arBtnPlantilla[1][3].setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Loteria.this.control(Loteria.this.n, 1, 3);
            }
        });
        this.arBtnPlantilla[2][0].setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Loteria.this.control(Loteria.this.n, 2, 0);
            }
        });
        this.arBtnPlantilla[2][1].setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Loteria.this.control(Loteria.this.n, 2, 1);
            }
        });
        this.arBtnPlantilla[2][2].setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Loteria.this.control(Loteria.this.n, 2, 2);
            }
        });
        this.arBtnPlantilla[2][3].setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Loteria.this.control(Loteria.this.n, 2, 3);
            }
        });
        this.arBtnPlantilla[3][0].setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Loteria.this.control(Loteria.this.n, 3, 0);
            }
        });
        this.arBtnPlantilla[3][1].setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Loteria.this.control(Loteria.this.n, 3, 1);
            }
        });
        this.arBtnPlantilla[3][2].setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Loteria.this.control(Loteria.this.n, 3, 2);
            }
        });
        this.arBtnPlantilla[3][3].setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Loteria.this.control(Loteria.this.n, 3, 3);
            }
        });
        this.vBox = new VBox();
        this.vBox.setSpacing(5.0);
        this.vBox.setPadding(new Insets(5.0));
        this.vBox.getChildren().addAll(new Node[]{this.hBox1, this.hBox2, this.btnJugar});
        this.escena = new Scene(this.vBox, 543.0, 528.0);
        this.escena.getStylesheets().add("sample/css/Loteria.css");
    }

    public void PlatillaInicio() {
        int carta = 0;

        for(int i = 0; i < 4; ++i) {
            for(int j = 0; j < 4; ++j) {
                this.arBtnPlantilla[i][j] = new Button();
                this.imgCartaP = new Image("sample/images/" + this.arImages2[0][carta]);
                this.imv = new ImageView(this.imgCartaP);
                this.imv.setFitWidth(70.0);
                this.imv.setFitHeight(100.0);
                this.arBtnPlantilla[i][j].setGraphic(this.imv);
                this.gdpPlantilla.add(this.arBtnPlantilla[i][j], j, i);
                ++carta;
            }
        }

    }

    private void control(int n, int reg, int col) {
        int aux = 0;
        System.out.println("Plantilla : " + this.cont);

        for(int i = 0; i < this.arBtnPlantilla.length; ++i) {
            for(int j = 0; j < this.arBtnPlantilla[0].length; ++j) {
                System.out.println(this.arImages[n] + this.arImages2[this.cont][aux]);
                if (i == reg && j == col && this.arImages[n].equalsIgnoreCase(this.arImages2[this.cont][aux])) {
                    --this.CartasFaltantes;
                    String cartaF = "";
                    cartaF = cartaF + this.CartasFaltantes;
                    this.lblCartaFalt.setText(cartaF);
                    this.BorraCarta(i, j);
                    if (this.vectoraux == 25) {
                        this.Resultado(this.CartasFaltantes);
                        this.cerrar = true;
                        this.animacion.stop();
                    }

                    if (this.CartasFaltantes != 0) {
                        this.NuevaTarjeta();
                        this.cerrar = false;
                        this.animacion.playFromStart();
                    } else {
                        this.Resultado(this.CartasFaltantes);
                        this.animacion.stop();
                        this.cerrar = true;
                    }
                }

                ++aux;
                System.out.println("Aux : " + aux);
            }
        }

    }

    private void BorraCarta(int cont, int aux) {
        Image imgBorradoTemp = new Image("sample/images/Borrar.jpg");
        ImageView imgBorrado = new ImageView(imgBorradoTemp);
        imgBorrado.setFitWidth(70.0);
        imgBorrado.setFitHeight(100.0);
        this.arBtnPlantilla[cont][aux].setGraphic(imgBorrado);
        this.arBtnPlantilla[cont][aux].getBackground();
        this.arBtnPlantilla[cont][aux].setDisable(true);
    }

    private void CrearPlantilla(int pos) {
        int carta = 0;

        for(int i = 0; i < 4; ++i) {
            for(int j = 0; j < 4; ++j) {
                this.imgCartaP = new Image("sample/images/" + this.arImages2[pos][carta]);
                this.imv = new ImageView(this.imgCartaP);
                this.imv.setFitWidth(70.0);
                this.imv.setFitHeight(100.0);
                this.arBtnPlantilla[i][j].setGraphic(this.imv);
                ++carta;
            }
        }

    }

    public void handle(Event event) {
        this.btnSiguiente.setDisable(true);
        this.btnAtras.setDisable(true);
        this.btnJugar.setDisable(true);
        final int[] v_tiempo = new int[]{1};
        this.tmTiempo = new Timer();
        this.tmtTarea = new TimerTask() {
            public void run() {
                if (v_tiempo[0] > 0) {
                    int conserva = v_tiempo[0];
                    Platform.runLater(() -> {
                        Loteria.this.lblTiempo.setText("00:" + conserva);
                    });
                    int var10002 = v_tiempo[0]++;
                    if (conserva == 250) {
                        Loteria.this.tmtTarea.cancel();
                    }

                    if (Loteria.this.vectoraux == 25) {
                        Loteria.this.tmtTarea.cancel();
                    }
                } else {
                    v_tiempo[0] = 0;
                }

            }
        };
        this.tmTiempo.scheduleAtFixedRate(this.tmtTarea, 1000L, 1000L);
        this.NuevaTarjeta();
        this.animacion = new Timeline(new KeyFrame[]{new KeyFrame(Duration.seconds(1.0), new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Loteria.this.NuevaTarjeta();
                if (Loteria.this.vectoraux == 25 && !Loteria.this.cerrar) {
                    Alert resul;
                    if (Loteria.this.CartasFaltantes == 0) {
                        resul = new Alert(AlertType.INFORMATION);
                        resul.setTitle("Felicidades");
                        resul.setHeaderText("Usted ha ganado la loteria");
                        resul.setContentText("");
                        resul.show();
                    } else {
                        resul = new Alert(AlertType.INFORMATION);
                        resul.setTitle("Sigue intentandolo");
                        resul.setHeaderText("Usted ha Perdido!!!");
                        resul.setContentText("");
                        resul.show();
                    }

                    Loteria.this.animacion.stop();
                }

            }
        }, new KeyValue[0])});
        this.animacion.setCycleCount(25);
        this.animacion.play();
    }

    private void Resultado(int cartasFaltantes) {
        Alert resul;
        if (this.CartasFaltantes == 0) {
            resul = new Alert(AlertType.INFORMATION);
            resul.setTitle("Felicidades");
            resul.setHeaderText("Usted ha ganado la loteria");
            resul.setContentText("");
            resul.show();
        } else {
            resul = new Alert(AlertType.INFORMATION);
            resul.setTitle("Sigue intentandolo");
            resul.setHeaderText("Usted ha Perdido!!!");
            resul.setContentText("");
            resul.show();
        }

    }

    private void Cerrar() {
        this.Resultado(this.CartasFaltantes);
        this.close();
    }

    private void cerrar(ActionEvent event) {
        Node sourse = (Node)event.getSource();
        Stage stage = (Stage)sourse.getScene().getWindow();
        stage.close();
    }

    private void NuevaTarjeta() {
        boolean opc;
        do {
            this.n = (int)(Math.random() * 25.0 + 0.0);
            opc = this.verificar(this.cartas, this.n, this.control);
            if (this.n == 0) {
                ++this.control;
            }

            if (opc) {
                this.cartas[this.vectoraux] = this.n;
                ++this.vectoraux;
                System.out.println("Vector aux :" + this.vectoraux);
                this.imgCarta = new Image("sample/images/" + this.arImages[this.n]);
                this.imgJ = new ImageView(this.imgCarta);
                this.imgJ.setFitWidth(135.0);
                this.imgJ.setFitHeight(240.0);
                this.btnCarta.setGraphic(this.imgJ);
            }
        } while(!opc);

    }

    private void imprimirVec(int[] cartas) {
        for(int i = 0; i < cartas.length; ++i) {
            System.out.println(cartas[i]);
        }

    }

    private boolean verificar(int[] cartas, int n, int control) {
        for(int i = 0; i < 25; ++i) {
            if (n == 0 && control == 0) {
                ++control;
                return true;
            }

            if (cartas[i] == n) {
                return false;
            }
        }

        return true;
    }
}