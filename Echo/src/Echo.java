 
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

 

public class Echo {
	
 	public static void main(String[] args) {
        System.out.println("------");
        final Monitor m = new Monitor();
//        ArrayList<P1>Knoten = new ArrayList<>();
//        Knoten.add(new P1("a", true, m));
//        Knoten.add(new P1("b", false, m));
//        Knoten.add(new P1("c", false, m));
//        
//        Knoten.get(0).setupNeighbours(Knoten.get(1));
//        Knoten.get(1).setupNeighbours(Knoten.get(2));
//        Knoten.get(2).setupNeighbours(Knoten.get(0));
//		for(P1 x:Knoten) {
//		x.start();	
//		}
	    

	    P1 a = new P1("a", true, m);
	    P1 b = new P1("b", false, m);
	    P1 c = new P1("c", false, m);
	    P1 d = new P1("d", false, m);
	    P1 e = new P1("e", false, m);
	    P1 f = new P1("f", false, m);


	    
 	    a.setupNeighbours(b ,c,d,e,a);
	    c.setupNeighbours(d,e);
	    d.setupNeighbours(e);
	    b.setupNeighbours(c,d,e,b);
	    f.setupNeighbours(a,d);

	    a.start();
	    b.start();
	    c.start();
	    d.start();
	    e.start();
	    f.start();
	  }
}

class Monitor {
    
    

	public Monitor() {
		
	}
	
  
	ArrayList<String> nodes=new ArrayList<String>();
 
	 public synchronized void print() throws IOException{
	    	
	    	// hier wird eine .dot datei erstellt man kann diese Datei zu PNG konvertieren damit 
	    	// man ein Photo vom dem Spannbaum sehen also kann graphish dargestellt dafür braucht mann allerdings ein Hilfprogramm
	    	// das ist das Grphviz programm 
	    	
	    	// zu Konevertieren wir der Befehel  dot -Tpng .\Graph.dot -o output.png
	    	
	    	 BufferedWriter writer;
			 writer = new BufferedWriter(new FileWriter("Graph.dot"));
			 writer.write("digraph G {");
	    	for(int i =0;i<nodes.size();++i) {
	    		 writer.append("\n"+nodes.get(i));
	    		 System.out.println(nodes.get(i));
	    	}
	    	writer.append("}");
	    		writer.close();
	    	
	    }
		 

	
    private boolean bool;
	public Monitor(boolean b) {
		this.bool = b;
	}
    public synchronized void set(boolean b){
		this.bool = b;
	}
	
	public synchronized boolean get() {
		return this.bool;
	}
	

	public synchronized void warten(P1 x) {
		while (!(x.wach.get())) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		notifyAll();
	

	
		while (x.getNachrichtenAnzahl() != x.getNachbarenAnzahl()) { // received
			try {
				System.out.println("tse");
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		notifyAll();
	}
	
	
	
 
	public void spannbaum(){
		boolean b = false;
		for(String i :  nodes) {
 			char[] a = i.toCharArray();
			char haupt_knote = a[0];
			int counters  = 0;
			int index=0;
			for(char c : a) {
				
				if(c != ' '&& c !='[' && c != ']' && c !=',') {
					if(index!=0)
					System.out.println(haupt_knote +"------>"+ c);
					for(String  x :  nodes) {
						if(i.equals(x)) {
							
						}else {
					 
							if(x.indexOf(haupt_knote)!=-1) {
								System.out.print(haupt_knote +" in  "+x);

								if(x.indexOf(c)!=-1) {
									System.out.print(c +"  "+counters);
									++counters;
									if(index!=0)
									if(counters>=2)
										b=true;
 									
								}
							}
						}
					}
					System.out.println();
					++index;
				}
			}
 
			
		}
		
		
		if(b) {
			System.out.println("ist kein  Spannbaum");
		}else {
			System.out.println("ist ein Spannbaum ");

		}
	}
}




class P1 extends NodeAbstract implements Runnable {
	
	public P1(String name, boolean initiat, Monitor m) {
		super(name, initiat, m);
		wach = new Monitor(false);
 		if (initiator) {
			wach.set(true);
		}
		
 	}
	
 
	private Node weker;
	private int nachrichtenAnzahl;
	public Monitor wach;
	
 	
	// graph ist für die ausgabe 
	public void graph() {
		m.nodes.add(this.toString() + " " + this.neighbours);
	}
	
	
	@Override
	public void hello(Node neighbour) {
		this.neighbours.add(neighbour);

 	}

	
	// a   (b ,c)
	
	// b  (c ,d ) a
	
	// c  (d) b
	
	// d
	@Override
	public synchronized void wakeup(Node neighbour) {
		if (!wach.get()) {
			wach.set(true);
			weker = neighbour;
			nachrichtenAnzahl = getNachrichtenAnzahl() + 1;
			notifyAll();

			System.out.println(this.toString() + " wacht von " + weker);
		} else {
			nachrichtenAnzahl = getNachrichtenAnzahl() + 1;
			notifyAll();

		}
 	}
	
	@Override
	public synchronized void echo(Node neighbour,Object data) {
		m.nodes.add(this.toString()+"---->"+neighbour.toString());
		nachrichtenAnzahl = getNachrichtenAnzahl() + 1;
		notifyAll();

	
 	}

	@Override
	public void setupNeighbours(Node... neighbours) {
 		for(int i = 0; i<neighbours.length;++i) {
			this.neighbours.add(neighbours[i]);
			neighbours[i].hello(this);
		}
	}
	
	@SuppressWarnings("static-access")
	@Override
	public void run() {
		// 
		
		synchronized(this){
			while (!(wach.get())) {
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			notifyAll();
		

		}
		
		//m.warten(this);
		Iterator<Node> iterator=this.neighbours.iterator();
		 while(iterator.hasNext()){
				Node temp=iterator.next();
	            temp.wakeup(this);
	        }
		
 		
		//m.warten(this);
		 synchronized(this) {
			 while (getNachrichtenAnzahl() != getNachbarenAnzahl()) { // received
					try {
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				notifyAll();
		 }
		if (!initiator&&  wach.get()) {
			weker.echo(this ,"");
			wach.set(false);
			
  		} else if (initiator) {
			
			try {
				sleep(1000);
 			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				m.warten(this);
				System.out.println("ich bin der intitiator ich beende den Process "+(m.nodes.size()+1)+" ist die Anzahl der Knoten");
				m.print();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}


	public int getNachrichtenAnzahl() {
		return nachrichtenAnzahl;
	}
	public int getNachbarenAnzahl() {
		return nachrichtenAnzahl;
	}
	
	
}
