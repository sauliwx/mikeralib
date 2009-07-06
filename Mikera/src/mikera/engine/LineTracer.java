package mikera.engine;

import mikera.util.Maths;

public class LineTracer {
	public abstract static class TraceFunction {
		public abstract boolean visit(int x, int y, int z);
	}

	
	public static float trace(double x1, double y1, double z1, double x2, double y2, double z2, TraceFunction func) {
		return trace((float)x1,(float)y1,(float)z1,(float)x2,(float)y2,(float)z2,func);
	}

	public static float trace(float x1, float y1, float z1, float x2, float y2, float z2, TraceFunction func) {
		int x=(int)Math.floor(x1);
		int y=(int)Math.floor(y1);
		int z=(int)Math.floor(z1);

		if (func.visit(x,y,z)) return 0.0f;

		float dx=x2-x1;
		float dy=y2-y1;
		float dz=z2-z1;
		
		int step_x=Maths.sign(dx);
		int step_y=Maths.sign(dy);
		int step_z=Maths.sign(dz);
		
		float totalDist=(float)Math.sqrt(dx*dx+dy*dy+dz*dz); // total distance
		float idd=1.0f/totalDist;
		float currentDist=0.0f; // distance so far
		
		dx*=idd;
		dy*=idd;
		dz*=idd;
		
		while(currentDist<totalDist) {
			float distanceIncrement=totalDist-currentDist;
			int chg=0; // bits for index change
			
			if (step_x!=0) {
				float changeDistance=(((step_x>0)?(x+1):x)-x1)/dx;
				if (changeDistance<=distanceIncrement) {
					chg=1;
					distanceIncrement=changeDistance;
				}
			}
			
			if (step_y!=0) {
				float changeDistance=(((step_y>0)?(y+1):y)-y1)/dy;
				if (changeDistance<=distanceIncrement) {
					if (changeDistance<distanceIncrement) {
						chg=2;
					} else {
						chg|=2;
					}
					distanceIncrement=changeDistance;
				}
			}
			
			if (step_z!=0) {
				float changeDistance=(((step_z>0)?(z+1):z)-z1)/dz;
				if (changeDistance<=distanceIncrement) {
					if (changeDistance<distanceIncrement) {
						chg=4;
					} else {
						chg|=4;
					}
					distanceIncrement=changeDistance;
				}
			}
			
			// update position
			currentDist+=distanceIncrement;
			if ((chg==0)||(currentDist>=totalDist)) return totalDist; // must have covered whole line
			if ((chg&1)>0) {
				x+=step_x;
			}
			if ((chg&2)>0) {
				y+=step_y;
			}
			if ((chg&4)>0) {
				z+=step_z;
			}
			
			if (func.visit(x,y,z)) return currentDist;
			
			// update location
			x1+=dx*distanceIncrement;
			y1+=dy*distanceIncrement;
			z1+=dz*distanceIncrement;		
		}

		return totalDist;
	}
}
