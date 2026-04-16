/*
Unlam está desarrollando un Sistema para la administración de las aulas del futuro. Para
eso se sabe que cada escritorio tendrá un mecanismo para identificar si se encuentra en
uso o no, permitiendo que cuando se organicen determinados eventos, de forma rápida se
pueda determinar si el aula está completa en su totalidad o en su defecto aún puede
ingresar nuevos asistentes, pudiendo además determinar muy rápidamente la ubicación
donde puede ubicarse. Para eso se solicita diseñar una clase en java que permita
administrar esta información, simplemente almacenando en un array bidimensional la
ubicación de cada escritorio, y pudiendo determinar si el mismo se encuentra libre u
ocupado.
Además, debe permitir múltiples aulas. Por lo cual voy a tener un array para almacenar estas,
suponiendo que hay 10 aulas.
*/
import java.util.Scanner;
public class Main{
	//constantes
	private static final String[]OPCIONES={"Listar aulas","Agregar aula","Estado de aula","Eliminar aula",
			"Ocupar escritorio","Desocupar escritorio"};
	//atributos
	private static Scanner teclado=new Scanner(System.in);
	private static AdminAulasUnlam sistema=new AdminAulasUnlam();
	//metodos útiles
	private static int getInt(){
		boolean error;
		int n=0;
		//valido la entrada hasta que se ingrese un entero propiamente
		do{
			error=false;
			try{
				n=teclado.nextInt();
			}
			catch(Exception e){
				error=true;
			}
		}while(error);
		//retorno el entero
		return n;
	}
	private static boolean cancelarOperacion(int input){
		boolean cancelar=input==0;
		//si la entrada es 0 printer y devolver true
		if(cancelar){
			System.out.println("Operación cancelada.");
		}
		//return
		return cancelar;
	}
	private static int validarId(int caso){
		//casos: 1=agregar, 2=ocupar, 3=resto
		int id;
		String error;
		boolean condicion;
		switch(caso){
			//si agrego y no hay espacio
			case 1:
				condicion=!sistema.pudedoAgregarAula();
				error="Máximo de aulas alcanzado!";
			break;
			//si no agrego y no hay aulas
			case 2,3:
				condicion=!sistema.hayAulas();
				error="No hay aulas cargadas!";
			break;
			//entrada incorrecta
			default:
				return -1;
		}
		//verifico errores segun el switch anterior
		if(condicion){
			System.out.println(error);
			return -1;
		}
		//listo aulas
		listarAulas();
		//indico retorno rapido
		System.out.println("*Si en algun momento desea cancelar la operación, ingrese un 0(cero)*");
		switch(caso){
			case 1:
				condicion=sistema.existeAula(id);
			break;
			case 2:
				condicion=!sistema.existeAula(id);
			break;
			case 3:
				condicion=!sistema.existeAula(id);
			break;
		}
		//valido id segun switch anterior
		//mientras que (no agregue Y el id no exista) O (agregue Y el id ya exista)
		System.out.print("Ingrese el id(entero>0, no repetible) del aula: ");
		do{
			id=getInt();
			//retorno rapido
			if(cancelarOperacion(id)){
				return 0;
			}
		}while(id<0||condicion);
		//retorno id validada
		return id;
	}
	//metodos menú
	private static void listarAulas(){
		//si no hay aulas, retorno
		if(!sistema.hayAulas()){
			return;
		}
		//por cada aula formateada como string, printear
		for(String aula:sistema.formatearAulas()){
			System.out.println(aula);
		}
		//espacio extra
		System.out.println();
	}
	private static void agregarAula(){
		int id,cantFilas,cantColumnas;
		//valido id
		id=validarId(true);
		if(id<1){
			return;
		}
		//valido cantidad de filas
		System.out.print("Ingrese cantidad de filas(entero>0): ");
		do{
			cantFilas=getInt();
			//retorno rapido
			if(cancelarOperacion(cantFilas)){
				return;
			}
		}while(cantFilas<1);
		//valido cantidad de columnas
		System.out.print("Ingrese cantidad de columnas(entero>0): ");
		do{
			cantColumnas=getInt();
			//retorno rapido
			if(cancelarOperacion(cantColumnas)){
				return;
			}
		}while(cantColumnas<1);
		//agrego
		sistema.agregarAula(id,cantFilas,cantColumnas);
		System.out.print("Se agregó el aula con id "+id+" correctamente.");
	}
	private static void estadoAula(){
		int id;
		//valido id
		id=validarId(false);
		if(id<1){
			return;
		}
		//printeo
		System.out.println(sistema.formatearAula(id));
	}
	private static void eliminarAula(){
		int id;
		//valido id
		id=validarId(false);
		if(id<1){
			return;
		}
		//elimino
		sistema.eliminarAula(id);
		System.out.println("Se eliminó el aula con id "+id+" correctamente.");
	}
	private static int[]ocuparEscritorio(){
		int id;
		//valido id
		id=validarId(false);
		if(id<1){
			return null;
		}
		System.out.println("Se ocupó el aula con id "+id+" correctamente.");
		return sistema.ocuparAula(id);
	}
	private static void desocuparEscritorio(){
		int id,fila,columna;
		//validar id
		id=validarId(false);
		if(id<1){
			return;
		}
		//si no hay espacio
		if(!sistema.puedoOcuparAula(id)){
			System.out.println();
			return;
		}
		//valido fila
		
		System.out.print("Ingrese fila(entero>0): ");
		do{
			fila=getInt();
			//retorno rapido
			if(cancelarOperacion(fila)){
				return;
			}
		}while(fila<1);
		//valido columna
		System.out.print("Ingrese columna(entero>0): ");
		do{
			columna=getInt();
			//retorno rapido
			if(cancelarOperacion(columna)){
				return;
			}
		}while(columna<1);
		//agrego
		}
	public static void main(String[]args){
		int opcion;
		do{
			//printeo menu
			System.out.println("-AdminAulasUnlam-");
			System.out.println("Seleccione una opción:");
			for(int i=0;i<OPCIONES.length;i++){
				System.out.println((i+1)+"."+OPCIONES[i]);
			}
			System.out.println("0.Salir");
			//discrimino la entrada
			opcion=getInt();
			switch(opcion){
				case 1:
					listarAulas();
				break;
				case 2:
					agregarAula();
				break;
				case 3:
					estadoAula();
				break;
				case 4:
					eliminarAula();
				break;
				case 5:
					ocuparEscritorio();
				break;
				case 6:
					desocuparEscritorio();
				break;
				case 0:
					System.out.print("-FÍN DEL PROGRAMA-");
				break;
				default:
					System.out.println("La opción ingresada es incorrecta!");
			}
		}while(opcion!=0);
	}
}