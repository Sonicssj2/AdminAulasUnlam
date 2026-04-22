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
		System.out.println("***Si en algun momento desea cancelar la operación, ingrese un 0(cero)***");
		//indico carga de id
		System.out.print("Ingrese el id(entero >= 1 no repetible) del aula: ");
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
			//indico si hubo error
			if(condicion){
				System.out.println(error);
			}
		}while(condicion);
		//retorno id validada
		return id;
	}
	private static int validarFila(int caso,int id){
		int fila,cantidadFilas;
		boolean condicion;
		//obtengo el tope de filas segun el caso
		switch(caso){
		case AGREGAR_AULA:
			cantidadFilas=Aula.MAX_FILAS;
			break;
		case DESOCUPAR_ESCRITORIO:
			cantidadFilas=sistema.getCantidadFilasAula(id);
			break;
		default:
			System.out.println("Argumento de caso incorrecto!");
			return -1;
		}
		//informo carga de fila
		System.out.print("Ingrese fila(entero entre 1 y "+cantidadFilas+" incluidos): ");
		//valido
		do{
			//valido dominio de fila
			do{
				fila=getInt();
			}while(fila<0||fila>cantidadFilas);
			//retorno rapido
			if(cancelarOperacion(fila)){
				return 0;
			}
			//valido segun el caso
			if(caso==AGREGAR_AULA){
				return fila;
			}
			condicion=!sistema.filaDesocupableAula(id,fila-1);
			//indico si hubo error
			if(condicion){
				System.out.println("La fila ingresada no tiene escritorios desocupalbes!");
			}
		}while(condicion);
		return fila;
	}
	private static int validarColumna(int caso,int id,int fila){
		int columna,cantidadColumnas;
		boolean condicion;
		//obtengo el tope de columnas segun el caso
		switch(caso){
		case AGREGAR_AULA:
			cantidadColumnas=Aula.MAX_COLUMNAS;
			break;
		case DESOCUPAR_ESCRITORIO:
			cantidadColumnas=sistema.getCantidadColumnasAula(id);
			break;
		default:
			System.out.println("Argumento de caso incorrecto!");
			return -1;
		}
		//informo carga de columna
		System.out.print("Ingrese columna(entero entre 1 y "+cantidadColumnas+" incluidos): ");
		//valido
		do{
			//valido dominio de columna
			do{
				columna=getInt();
			}while(columna<0||columna>cantidadColumnas);
			//retorno rapido
			if(cancelarOperacion(columna)){
				return 0;
			}
			//valido segun el caso
			if(caso==AGREGAR_AULA){
				return columna;
			}
			condicion=!sistema.escritorioDesocupableAula(id,fila-1,columna-1);
			//indico si hubo error
			if(condicion){
				System.out.println("El escritorio de fila "+fila+", columna "+columna+", del aula "+id+" ya esta desocupado!");
			}
		}while(condicion);
		return columna;
	}
	//metodos menú
	private static void listarAulas(){
		//si no hay aulas, retorno
		if(!sistema.hayAulas()){
			System.out.println("No hay aulas para listar!");
			return;
		}
		//por cada aula formateada como string, printear
		for(String aula:sistema.formatearAulas()){
			System.out.println(aula);
		}
	}
	private static void agregarAula(){
		int id,cantidadFilas,cantidadColumnas;
		//valido id
		id=validarId(AGREGAR_AULA);
		if(id<1){
			return;
		}
		//valido cantidad de filas
		cantidadFilas=validarFila(AGREGAR_AULA,0);
		if(cantidadFilas<1){
			return;
		}
		//valido cantidad de columnas
		cantidadColumnas=validarColumna(AGREGAR_AULA,0,0);
		if(cantidadColumnas<1){
			return;
		}
		//agrego
		sistema.agregarAula(id,cantidadFilas,cantidadColumnas);
		System.out.println("Se agregó el aula con id "+id+" correctamente.");
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
		cantidadFilas=sistema.getCantidadFilasAula(id);
		fila=posicion/cantidadFilas;
		columna=posicion%cantidadFilas;
		//informo la posición ocupada por fila y columna
		System.out.println("Se ocupó el aula con id "+id+" correctamente.");
		System.out.println("Su escritorio se encuentra en la fila "+(fila+1)+", columna "+(columna+1)+".");
	}
	private static void desocuparEscritorio(){
		int id,fila,columna;
		//validar id
		id=validarId(DESOCUPAR_ESCRITORIO);
		if(id<1){
			return;
		}
		//validar fila
		fila=validarFila(DESOCUPAR_ESCRITORIO,id);
		if(fila<1){
			return;
		}
		//validar columna
		columna=validarColumna(DESOCUPAR_ESCRITORIO,id,fila);
		if(columna<1){
			return;
		}
		//desocupo
		sistema.desocuparAula(id,fila-1,columna-1);
		//informo la posición desocupada por fila y columna
		System.out.println("Se desocupó el aula con id "+id+" correctamente.");
		System.out.println("El escritorio desocupado fué el: fila "+fila+", columna "+columna+".");
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
					System.out.println("-FÍN DEL PROGRAMA-");
			}
			for(int i=0;i<50;i++){
				System.out.print("-");
			}
			System.out.println();
		}while(opcion!=SALIR);
	}
}