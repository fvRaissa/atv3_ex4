package view;
import java.util.concurrent.Semaphore;
import controller.Formula1;
public class Main {

	public static void main(String[] args) {
		
		int permissoes = 1;
		
		Semaphore semaforo = new Semaphore(5);
		Semaphore equipe1 = new Semaphore(permissoes);
		Semaphore equipe2 = new Semaphore(permissoes);
		Semaphore equipe3 = new Semaphore(permissoes);
		Semaphore equipe4 = new Semaphore(permissoes);
		Semaphore equipe5 = new Semaphore(permissoes);
		Semaphore equipe6 = new Semaphore(permissoes);
		Semaphore equipe7 = new Semaphore(permissoes);
		
		for(int i = 0; i<7; i++) {
			for(int j = 0;j<2;j++) {
				Formula1 f = new Formula1(i+1,j+1,semaforo,equipe1,equipe2,equipe3,equipe4,equipe5,equipe6,equipe7);
				f.start();
			}
		}
		

	}

}
