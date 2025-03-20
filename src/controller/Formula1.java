package controller;
import java.util.concurrent.Semaphore;
public class Formula1 extends Thread {
	
	private static int[][] tempo = new int[14][3];
	private static int posiChegada;
	private int idEquipe;
	private int carro;
	private Semaphore semaforo;
	private Semaphore equipe1;
	private Semaphore equipe2;
	private Semaphore equipe3;
	private Semaphore equipe4;
	private Semaphore equipe5;
	private Semaphore equipe6;
	private Semaphore equipe7;
	
	public Formula1(int idEquipe, int carro, Semaphore semaforo, Semaphore equipe1, Semaphore equipe2, Semaphore equipe3,
			Semaphore equipe4, Semaphore equipe5, Semaphore equipe6, Semaphore equipe7) {
		this.idEquipe = idEquipe;
		this.carro = carro;
		this.semaforo = semaforo;
		this.equipe1 = equipe1;
		this.equipe2 = equipe2;
		this.equipe3 = equipe3;
		this.equipe4 = equipe4;
		this.equipe5 = equipe5;
		this.equipe6 = equipe6;
		this.equipe7 = equipe7;
	}
	
	public void run() {
		try {
			switch(this.idEquipe) {
				case 1: equipe1.acquire();break;
				case 2: equipe2.acquire();break;
				case 3: equipe3.acquire();break;
				case 4: equipe4.acquire();break;
				case 5: equipe5.acquire();break;
				case 6: equipe6.acquire();break;
				case 7: equipe7.acquire();break;
			}
			semaforo.acquire();
			volta();
			
		}catch(InterruptedException e) {
			e.printStackTrace();
		}finally {
			switch(this.idEquipe) {
			case 1: equipe1.release();break;
			case 2: equipe2.release();break;
			case 3: equipe3.release();break;
			case 4: equipe4.release();break;
			case 5: equipe5.release();break;
			case 6: equipe6.release();break;
			case 7: equipe7.release();break;
			}
			semaforo.release();
			chegada();
		}
	}

	private void volta() {
		int[] voltas = new int[3];
		System.out.println("O carro "+carro+" da equipe "+idEquipe+" entrou na pista!");
		
		for(int i =0;i<3;i++){
			int tempoVolta = (int)(Math.random()*3000+2000);
			
			try {
				sleep(tempoVolta);
				
				voltas[i] = tempoVolta;
				int minutos = (tempoVolta / 1000)/60;
				int segundos = (tempoVolta/1000)%60;
				
				System.out.println("O carro "+carro+ " da equipe "+idEquipe+" fez a "+(i+1)+"° volta em " +String.format("%02d",minutos)+":"+ String.format("%02d",segundos));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		for(int i = 0;i<2;i++) {
			for(int j = i; j < 3; j++) {
				if(voltas[i]> voltas[j]) {
					int tempo = voltas[i];
					voltas[i] = voltas[j];
					voltas[j] = tempo;
				}
			}
		}
		
		int[] dados = { voltas[0], idEquipe, carro};
		tempo[posiChegada++] = dados;
	}

	private void chegada() {
		int minutos = (tempo[posiChegada-1][0]/1000)/60;
		int segundos = (tempo[posiChegada-1][0]/1000)%60;
		
		System.out.println("O carro "+carro+" da equipe "+idEquipe+ "ultrapassou a ultima volta.\n melhor volta: "+String.format("%02d",minutos)+":"+ String.format("%02d",segundos));
		if(posiChegada == 14) {
			System.out.println("GRID DE LARGADA:");
			for(int i = 0; i<13;i++) {
				for(int j = i; j<14;j++) {
					if(tempo[i][0]> tempo[j][0]) {
						int[]temp =  tempo[i];
						tempo[i] = tempo[j];
						tempo[j] = temp;
					}
				}
			}
			
			System.out.println("POSIÇÃO | EQUIPE | CARRO | TEMPO");
			for(int i =0;i<14;i++) {
				int minutosVolta = (tempo[i][0]/1000)/60;
				int segundosVolta = (tempo[i][0]/1000)%60;
				
				if(i<9) {
					System.out.println((i+1)+"	  "+tempo[i][1]+"	  "+tempo[i][2]+"	  "+String.format("%02d",minutosVolta)+":"+ String.format("%02d",segundosVolta));
				}else {
					System.out.println((i+1)+"	  "+tempo[i][1]+"	  "+tempo[i][2]+"	  "+String.format("%02d",minutosVolta)+":"+ String.format("%02d",segundosVolta));
				}
			}
		}
	}
	

}
