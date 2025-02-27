public class testDraw {
    public static void main(String[] args) {
        StdDraw.setTitle("planets");
        StdDraw.setCanvasSize(700, 700);

        StdDraw.setPenRadius(0.05);
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.point(0.5, 0.9);
        StdDraw.setPenColor(StdDraw.MAGENTA);
        StdDraw.filledRectangle(0.5, 0.3, 0.25, 0.25);
    }
}
