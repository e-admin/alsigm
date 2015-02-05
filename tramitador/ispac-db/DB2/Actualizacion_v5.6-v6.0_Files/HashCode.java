
public class HashCode {
	public static void main(String[] args) {
		if (args.length == 0){
			System.out.println("Llamada erronea. LLamada correcta: java HashCode <valor>");
			return;
		}
		System.out.println(Math.abs(args[0].toUpperCase().hashCode()));
	}
}
