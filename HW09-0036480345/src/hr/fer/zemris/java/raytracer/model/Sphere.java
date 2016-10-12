package hr.fer.zemris.java.raytracer.model;


/**
 * Implementation of {@linkplain GraphicalObject}. 
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class Sphere extends GraphicalObject {

	/** The center. */
	private Point3D center;
	
	/** The radius. */
	private double radius;
	
	/** The kdr. */
	private double kdr;
	
	/** The kdg. */
	private double kdg;
	
	/** The kdb. */
	private double kdb;
	
	/** The krr. */
	private double krr;
	
	/** The krg. */
	private double krg;
	
	/** The krb. */
	private double krb;
	
	/** The krn. */
	private double krn;

	/**
	 * Instantiates a new sphere.
	 *
	 * @param center the center
	 * @param radius the radius
	 * @param kdr the kdr
	 * @param kdg the kdg
	 * @param kdb the kdb
	 * @param krr the krr
	 * @param krg the krg
	 * @param krb the krb
	 * @param krn the krn
	 */
	public Sphere(Point3D center, double radius, double kdr, double kdg,
			double kdb, double krr, double krg, double krb, double krn) {

		super();
		this.center = center;
		this.radius = radius;
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn = krn;
	}

	public RayIntersection findClosestRayIntersection(Ray ray) {

		Point3D eyeToCenter = ray.start.sub(center);
		
		double a = 1;
		double b = ray.direction.scalarMultiply(2).scalarProduct(eyeToCenter);
		double c = eyeToCenter.scalarProduct(eyeToCenter)-Math.pow(radius,2);
		
		double lambda1 = (-b - Math.sqrt(Math.pow(b, 2)-4*a*c))/(2*a);
		double lambda2 = (-b + Math.sqrt(Math.pow(b, 2)-4*a*c))/(2*a);
		
		Point3D P;
		if (lambda1 != lambda2 && lambda1 > 0 && lambda2 > 0 ||
				(lambda1 == lambda2 && lambda1 > 0)){
			if (lambda1 > lambda2){
				lambda1 = lambda2;
			}
			P = ray.start.add(ray.direction.scalarMultiply(lambda1));
		} else return null;
		
		double distance = P.sub(ray.start).norm();
		
		Point3D normal = P.sub(center).normalize();

		return new RayIntersection(P, distance, true) {

			@Override
			public Point3D getNormal() {
				return normal;
			}

			@Override
			public double getKrr() {
				return krr;
			}

			@Override
			public double getKrn() {
				return krn;
			}

			@Override
			public double getKrg() {
				return krg;
			}

			@Override
			public double getKrb() {
				return krb;
			}

			@Override
			public double getKdr() {
				return kdr;
			}

			@Override
			public double getKdg() {
				return kdg;
			}

			@Override
			public double getKdb() {
				return kdb;
			}
		};
	}
}
