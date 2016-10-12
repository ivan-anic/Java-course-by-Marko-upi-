package hr.fer.zemris.java.raytracer;

import java.util.List;

import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.LightSource;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * Implementation of a {@linkplain Ray} caster. Produces {@linkplain Ray}s ,
 * traces them, and sets appropriate color values to all the visible
 * {@linkplain GraphicalObject}s.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class RayCaster {

	/** The default ambient component value. */
	private static short AMBIENT_COMPONENT = 15;

	/**
	 * The main method, first one to be executed when the program starts.
	 * 
	 * @param args
	 *            - the input arguments, not used here
	 */
	public static void main(String[] args) {

		RayTracerViewer.show(getIRayTracerProducer(),
				new Point3D(10, 0, 0),
				new Point3D(0, 0, 0),
				new Point3D(0, 0, 10),
				20, 20);
	}

	/**
	 * Instantiates a new {@linkplain IRayTracerProducer} and returns it.
	 *
	 * @return the desired ray tracer producer
	 */
	private static IRayTracerProducer getIRayTracerProducer() {
		return new IRayTracerProducer() {
			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp,
					double horizontal, double vertical, int width, int height,
					long requestNo, IRayTracerResultObserver observer) {

				System.out.println("Beginning calculations...");

				short[] red = new short[width * height];
				short[] green = new short[width * height];
				short[] blue = new short[width * height];

				Point3D zAxis = getZAxis(view, eye);
				Point3D yAxis = getYAxis(zAxis, viewUp);
				Point3D xAxis = getXAxis(zAxis, yAxis);
				Point3D screenCorner = getScreenCorner(view, horizontal, vertical, xAxis, yAxis);

				Scene scene = RayTracerViewer.createPredefinedScene();
				short[] rgb = new short[3];
				int offset = 0;

				for (int y = 0; y < height; y++) {
					for (int x = 0; x < width; x++) {
						Point3D screenPoint = getScreenPoint(x, y, screenCorner, xAxis, yAxis,
								width, height, horizontal, vertical);
						Ray ray = Ray.fromPoints(eye, screenPoint);

						tracer(scene, ray, rgb);

						red[offset] = rgb[0] > 255 ? 255 : rgb[0];
						green[offset] = rgb[1] > 255 ? 255 : rgb[1];
						blue[offset] = rgb[2] > 255 ? 255 : rgb[2];
						offset++;
					}
				}
				System.out.println("Calculations done...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Notifying the observer done...");
			}
		};
	}

	/**
	 * The {@linkplain Ray} tracer, traces the rays coming from the eye, and
	 * determines all the colors which will be visible.
	 *
	 * @param scene
	 *            the scene containing {@linkplain LightSource}s and
	 *            {@linkplain GraphicalObject}s.
	 * @param ray
	 *            the {@linkplain Ray} being traced
	 * @param rgb
	 *            an array which will contain the desired number values
	 */
	private static void tracer(Scene scene, Ray ray, short[] rgb) {
		List<GraphicalObject> objectList = scene.getObjects();
		List<LightSource> lightSourceList = scene.getLights();

		RayIntersection S = findNearestIntersection(objectList, ray);

		if (S == null) {
			rgb[0] = 0;
			rgb[1] = 0;
			rgb[2] = 0;
		} else
			determineColorFor(S, rgb, lightSourceList, objectList, ray);
	}

	/**
	 * Determines the color which the ray will produce
	 *
	 * @param S
	 *            the current {@linkplain RayIntersection} being inspected
	 * @param rgb
	 *            an array which will contain the desired number values
	 * 
	 * @param lightSourceList
	 *            the light source list
	 * @param objectList
	 *            the object list
	 * @param ray
	 *            the {@linkplain Ray} being traced
	 */
	private static void determineColorFor(RayIntersection S, short[] rgb,
			List<LightSource> lightSourceList, List<GraphicalObject> objectList, Ray ray) {

		rgb[0] = AMBIENT_COMPONENT;
		rgb[1] = AMBIENT_COMPONENT;
		rgb[2] = AMBIENT_COMPONENT;

		for (LightSource ls : lightSourceList) {
			Ray r = Ray.fromPoints(ls.getPoint(), S.getPoint());

			RayIntersection intersection = null;
			double dist = ls.getPoint().sub(S.getPoint()).norm();
			boolean isShadowed = false;

			for (GraphicalObject obj : objectList) {
				intersection = obj.findClosestRayIntersection(r);
				if (intersection != null) {
					double distance = ls.getPoint().sub(intersection.getPoint()).norm();
					if (Double.compare(distance + 0.01, dist) < 0) {
						isShadowed = true;
					}
				}
			}

			if (isShadowed) {
				isShadowed = false;
				continue;
			} else {
				setColor(rgb, ls, S, ray);
			}
		}

	}

	/**
	 * Find the nearest intersection of the ray and the objects in the object
	 * list.
	 *
	 * @param objectList
	 *            the given object list
	 * @param ray
	 *            the traced ray
	 * @return the desired ray intersection
	 */
	private static RayIntersection findNearestIntersection(List<GraphicalObject> objectList, Ray ray) {
		RayIntersection S = null;
		boolean found = false;

		for (GraphicalObject o : objectList) {
			RayIntersection temp = o.findClosestRayIntersection(ray);

			if (!found) {
				if (temp == null) {
					continue;
				}
				found = true;
				S = temp;
				continue;
			} else if (temp != null && temp.getDistance() < S.getDistance()) {
				S = temp;
			}
		}
		return S;
	}

	/**
	 * Sets the color of the pixel.
	 * 
	 * @param rgb
	 *            an array which will contain the desired number values
	 * @param ray
	 *            the {@linkplain Ray} being traced * @param rgb an array which
	 *            will contain the desired number values
	 * @param ls
	 *            the current light source
	 * @param RI
	 *            the current intersection
	 * 
	 */
	private static void setColor(short[] rgb, LightSource ls, RayIntersection RI, Ray ray) {
		Point3D l = ls.getPoint().sub(RI.getPoint()).normalize();
		Point3D n = RI.getNormal();
		Point3D r = l.sub(n.scalarMultiply(2)).scalarMultiply(l.scalarProduct(n)).normalize();
		Point3D v = ray.start.sub(RI.getPoint()).normalize();

		double pot = RI.getKrn();

		double scalarDiff = l.scalarProduct(n);
		double scalarRef = r.scalarProduct(v);

		scalarDiff = scalarDiff < 0 ? 0 : scalarDiff;
		scalarRef = scalarRef < 0 ? 0 : scalarRef;

		double resultR = ls.getR()
				* (RI.getKdr() * scalarDiff + RI.getKrr() * Math.pow(scalarRef, pot));
		double resultG = ls.getG()
				* (RI.getKdg() * scalarDiff + RI.getKrg() * Math.pow(scalarRef, pot));
		double resultB = ls.getB()
				* (RI.getKdb() * scalarDiff + RI.getKrb() * Math.pow(scalarRef, pot));

		rgb[0] += resultR;
		rgb[1] += resultG;
		rgb[2] += resultB;
	}

	/**
	 * Gets the z axis.
	 *
	 * @param view
	 *            the view
	 * @param eye
	 *            the eye
	 * @return the z axis
	 */
	private static Point3D getZAxis(Point3D view, Point3D eye) {
		return view.sub(eye).normalize();
	}

	/**
	 * Gets the y axis.
	 *
	 * @param zAxis
	 *            the z axis
	 * @param viewUp
	 *            the view up
	 * @return the y axis
	 */
	private static Point3D getYAxis(Point3D zAxis, Point3D viewUp) {
		return viewUp.normalize().sub(zAxis.scalarMultiply(zAxis.scalarProduct(viewUp.normalize())))
				.normalize();
	}

	/**
	 * Gets the x axis.
	 *
	 * @param zAxis
	 *            the z axis
	 * @param yAxis
	 *            the y axis
	 * @return the x axis
	 */
	private static Point3D getXAxis(Point3D zAxis, Point3D yAxis) {
		return zAxis.vectorProduct(yAxis).normalize();
	}

	/**
	 * Gets the screen corner.
	 *
	 * @param view
	 *            the view
	 * @param horizontal
	 *            the horizontal offset
	 * @param vertical
	 *            the vertical offset
	 * @param xAxis
	 *            the x axis
	 * @param yAxis
	 *            the y axis
	 * @return the screen corner
	 */
	private static Point3D getScreenCorner(Point3D view, double horizontal, double vertical,
			Point3D xAxis, Point3D yAxis) {

		Point3D horiz = xAxis.scalarMultiply(horizontal / 2);
		Point3D verti = yAxis.scalarMultiply(vertical / 2);
		return view.sub(horiz).add(verti);
	}

	/**
	 * Gets the screen point.
	 *
	 * @param x
	 *            the x coordinate
	 * @param y
	 *            the y coordinate
	 * @param corner
	 *            the corner
	 * @param xAxis
	 *            the x axis
	 * @param yAxis
	 *            the y axis
	 * @param width
	 *            the width of the window
	 * @param height
	 *            the height of the window
	 * @param horizontal
	 *            the horizontal offset
	 * @param vertical
	 *            the vertical offset
	 * @return the screen point
	 */
	private static Point3D getScreenPoint(int x, int y,
			Point3D corner, Point3D xAxis, Point3D yAxis,
			double width, double height, double horizontal, double vertical) {

		Point3D horiz = xAxis.scalarMultiply(horizontal * x / (width - 1));
		Point3D verti = yAxis.scalarMultiply(vertical * y / (height - 1));

		return corner.add(horiz).sub(verti);
	}
}