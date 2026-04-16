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
	private static final int LISTAR_AULAS=1,AGREGAR_AULA=2,ESTADO_AULA=3,ELIMINAR_AULA=4,
	OCUPAR_ESCRITORIO=5,DESOCUPAR_ESCRITORIO=6,SALIR=0;
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
		int id;
		String error;
		boolean condicion;
		
		switch(caso){
		//si agrego una aula y no hay mas espacio
		case AGREGAR_AULA:
			condicion=!sistema.pudedoAgregarAula();
			error="Máximo de aulas alcanzado!";
			break;
		//si chequeo estado, elimino, ocupo, o desocupo un aula y no hay aulas cargadas
		case ESTADO_AULA,ELIMINAR_AULA,OCUPAR_ESCRITORIO,DESOCUPAR_ESCRITORIO:
			condicion=!sistema.hayAulas();
			error="No hay aulas cargadas!";
			break;
		//si el parametro "caso" esta mal ingresado
		default:
			condicion=true;
			error="Argumento de caso incorrecto!";
		}
		//verifico errores segun el switch anterior
		if(condicion){
			System.out.println(error);
			return -1;
		}
		//si no chequeo estado listo las aulas
		if(caso!=ESTADO_AULA){
			listarAulas();
		}
		//indico retorno rapido
		System.out.println("*Si en algun momento desea cancelar la operación, ingrese un 0(cero)*");
		//indico carga de id
		System.out.print("Ingrese el id(entero>0, no repetible) del aula: ");
		//no hay error anterior
		condicion=false;
		//valido
		do{
			//valido dominio de id
			do{
				id=getInt();
			}while(id<0);
			//retorno rapido
			if(cancelarOperacion(id)){
				return 0;
			}
			//printeo error anterior si hay
			if(condicion){
				System.out.println(error);
			}
			//valido id segun el caso
			switch(caso){
			case AGREGAR_AULA:
				condicion=sistema.existeAula(id);
				if(condicion){
					error="Ya existe un aula con id "+id+"!";
				}
				break;
			case ESTADO_AULA,ELIMINAR_AULA:
				condicion=!sistema.existeAula(id);
				if(condicion){
					error="No se encontró un aula con id "+id+"!";
				}
				break;
			case OCUPAR_ESCRITORIO:
				condicion=!sistema.existeAula(id);
				if(condicion){
					error="No se encontró un aula con id "+id+"!";
				}
				else{
					condicion=!sistema.puedoOcuparAula(id);
					if(condicion){
						error="El aula con id "+id+" está llena!";
					}
				}
				break;
			case DESOCUPAR_ESCRITORIO:
				condicion=!sistema.existeAula(id);
				if(condicion){
					error="No se encontró un aula con id "+id+"!";
				}
				else{
					condicion=!sistema.puedoDesocuparAula(id);
					if(condicion){
						error="El aula con id "+id+" está vacía!";
					}
				}
				break;
			}
		}while(condicion);
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
	private static void agregarAula(){//TODO: validar rago fila, validar rango columna
		int id,cantFilas,cantColumnas;
		//valido id
		id=validarId(AGREGAR_AULA);
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
		id=validarId(ESTADO_AULA);
		if(id<1){
			return;
		}
		//printeo
		System.out.println(sistema.formatearAula(id));
	}
	private static void eliminarAula(){
		int id;
		//valido id
		id=validarId(ELIMINAR_AULA);
		if(id<1){
			return;
		}
		//elimino
		sistema.eliminarAula(id);
		System.out.println("Se eliminó el aula con id "+id+" correctamente.");
	}
	private static void ocuparEscritorio(){
		int id,posicion,cantidadFilas,fila,columna;
		//valido id
		id=validarId(OCUPAR_ESCRITORIO);
		if(id<1){
			return;
		}
		//obtengo posición ocupada
		posicion=sistema.ocuparAula(id);
		//calculo la fila y la columna
		cantidadFilas=sistema.getCantitdadFilasAula(id);
		fila=posicion/cantidadFilas;
		columna=posicion%cantidadFilas;
		//informo la posición ocupada por fila y columna
		System.out.println("Se ocupó el aula con id "+id+" correctamente.");
		System.out.println("Su escritorios se encuentra en la fila "+fila+", columna "+columna+".");
	}
	private static void desocuparEscritorio(){//TODO: validar fila y columna, no desocupar un escritorio desocupado
		int id,fila,columna;
		//validar id
		id=validarId(DESOCUPAR_ESCRITORIO);
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
		final String[]OPCIONES={"Listar aulas","Agregar aula","Estado de aula","Eliminar aula",
				"Ocupar escritorio","Desocupar escritorio"};
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
			do{
				opcion=getInt();
			}while(opcion<SALIR||opcion>OPCIONES.length);
			//ejecuto segun la entrada
			switch(opcion){
				case LISTAR_AULAS:
					listarAulas();
				break;
				case AGREGAR_AULA:
					agregarAula();
				break;
				case ESTADO_AULA:
					estadoAula();
				break;
				case ELIMINAR_AULA:
					eliminarAula();
				break;
				case OCUPAR_ESCRITORIO:
					ocuparEscritorio();
				break;
				case DESOCUPAR_ESCRITORIO:
					desocuparEscritorio();
				break;
				case SALIR:
					System.out.print("-FÍN DEL PROGRAMA-");
			}
		}while(opcion!=SALIR);
	}
}