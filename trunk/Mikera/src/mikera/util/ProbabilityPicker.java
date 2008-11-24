package mikera.util;

public final class ProbabilityPicker<O> {
	private static double[] NULLCHANCES=new double[0];
	private static Object[] NULLOBJECTS=new Object[0];
	
	private int count=0;
	private double[] chances=NULLCHANCES;
	private double[] belows=NULLCHANCES;
	private Object[] objects=NULLOBJECTS;
	private double total=0.0;
	
	/**
	 * Picks a random object
	 * 
	 * @return Random object chosen with associated relative probability
	 */
	@SuppressWarnings("unchecked")
	public O pick() {
		double r=Rand.nextDouble()*total;
		
		int i=0;
		int ir=1;
		for (;;) {
			double ci=chances[i];
			if (r<ci) return (O)objects[i];
			r-=ci;
			
			double bi=belows[i];
			if (r<bi) {
				i=childIndex(i,ir,0);
			} else {
				i=childIndex(i,ir,1);
				r-=bi;
			}
			
			ir*=2;
		}
	}
	
	private void ensureSize(int n) {
		if (n<=count) return;
		if (n>chances.length) {
			int nn=Math.max(n,(chances.length*2));
			double[] newChances=new double[nn];
			double[] newBelows=new double[nn];
			Object[] newObjects=new Object[nn];

			System.arraycopy(chances, 0, newChances,0,count);
			System.arraycopy(belows, 0, newBelows,0, count);
			System.arraycopy(objects, 0, newObjects,0,count);
			
			chances=newChances;
			objects=newObjects;
			belows=newBelows;
		}
	}
	
	
	public void add(O object, double probability) {
		if (probability<0) probability=0;
		
		ensureSize(count+1);
		
		int i=count;
		count++;
		
		objects[i]=object;

		setChance(i,probability);
	}
	
	public void set(O o, double p) {
		for (int i=0; i<count; i++) {
			if (objects[i].equals(o)) setChance(i,p);
		}
	}
	
	private void setChance(int i, double p) {
		double d=p-chances[i];
		total+=d;
		chances[i]=p;
		
		int r=order(i);
		while (r>1) {
			int pi=parentIndex(i,r);
			int pr=r/2; // parent order
			
			if (((i+1)&pr)>0) {
				// top branch
			} else {
				// update below
				belows[pi]+=d;
			}
			
			r=pr;
			i=pi;
		}
	}
	
	public double getTotal() {
		return total;
	}
	
	public double getCount() {
		return count;
	}
	
	public void remove(Object object) {
		for (int i=0; i<count; i++) {
			if (objects[i].equals(object)) {
				setChance(i,0);
				int last=count-1;
				
				if (i<last) {
					//move object from end
					double cd=chances[last];
					setChance(last,0);
					objects[i]=objects[last];
					setChance(i,cd);
					objects[last]=null;
				}
			
				count--;
				return;
			}
		}
	}
	
	public static int parentIndex(int i) {
		int po=order(i);
		return parentIndex(i,po);
	}
	
	private static int parentIndex(int i, int po) {
		return ( ((i+1)&(~po))|(po>>1) ) -1;
	}
	
	public static int childIndex(int i, int branch) {
		return i+(1+branch)*order(i);
	}
	
	private static int childIndex(int i, int ir, int branch) {
		return i+(1+branch)*ir;
	}
	
	public static int order(int i) {
		return Bits.roundUpToPowerOfTwo(i+2)/2;
	}
}