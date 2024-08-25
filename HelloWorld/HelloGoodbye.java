public class HelloGoodbye {
    public static void main(String[] args) {
        if (args.length < 2) {
            return;
        }
        String stringOne = String.format("Hello %s and %s.", args[0], args[1]);
        String stringTwo = String.format("Goodbye %s and %s.", args[1], args[0]);
        System.out.println(stringOne);
        System.out.println(stringTwo);
    }
}
