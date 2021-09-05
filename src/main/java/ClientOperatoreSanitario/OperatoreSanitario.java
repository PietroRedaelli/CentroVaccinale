package ClientOperatoreSanitario;

import Server.ServerInterface;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class OperatoreSanitario{


    public static void main(String[] args){
        try {
            Registry registro = LocateRegistry.getRegistry(1099);
            ServerInterface server = (ServerInterface) registro.lookup("CentroVaccinale");
        } catch (Exception e) {
            System.err.println("Client: errore di connessione al server \n" + e.getMessage());
            System.exit(0);
        }
    }

/*
    int id;
    Notizia notiziaPolitica;
    Notizia notiziaAttualita;
    Notizia notiziaScienza;
    Notizia notiziaSport;

    public OperatoreSanitario(int id) {
        super("Produttore Notizie "+id);
        this.id = id;
    }

    @Override
    public void run() {
        while (true) {
            creaNotizia();//genera notizie
            try {
                Thread.sleep(new Random().nextInt(3000)+1000);//attesa casuale
            } catch (InterruptedException e) {}
        }
    }

    private void creaNotizia(){
        String contenuto = "Notizia generata da " + getName();
        creaNotiziaPolitica(contenuto);
        creaNotiziaAttualita(contenuto);
        creaNotiziaScienza(contenuto);
        creaNotiziaSport(contenuto);
    }
    private void creaNotiziaPolitica(String contenuto) {
        if((new Random().nextInt(100))%10 == 0){//crea notizia Politica
            notiziaPolitica = new Notizia(TipoNotizia.POLITICA, contenuto);
        }
    }
    private void creaNotiziaAttualita(String contenuto) {
        if((new Random().nextInt(100))%10 == 0){//crea notizia Attualità
            notiziaAttualita = new Notizia(TipoNotizia.ATTUALITA, contenuto);
        }
    }
    private void creaNotiziaScienza(String contenuto) {
        if((new Random().nextInt(100))%10 == 0){//crea notizia Scienza
            notiziaScienza = new Notizia(TipoNotizia.SCIENZA, contenuto);
        }
    }
    private void creaNotiziaSport(String contenuto) {
        if((new Random().nextInt(100))%10 == 0){//crea notizia Sport
            notiziaSport = new Notizia(TipoNotizia.SPORT, contenuto);
        }
    }*/
}
