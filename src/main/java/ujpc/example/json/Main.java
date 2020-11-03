package ujpc.example.json;

public class Main {
    public static void main(String[] args) {
        final JsonParser parser = new JsonParser();
        final Json result = parser.parse(args[0]).getOrThrow();
        System.out.println(result);
    }
}
