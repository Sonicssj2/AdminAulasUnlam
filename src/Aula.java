public class Aula{
	//constantes
	public static final int MAX_FILAS=100;
	public static final int MAX_COLUMNAS=100;
	//atributos
	private int id;
	private boolean[][]escritorios=new boolean[MAX_FILAS][MAX_COLUMNAS];
	private int cantidadFilas;
	private int cantidadColumnas;
	private int escritoriosOcupados=0;
	private int[]posicionesRetornadas=new int[MAX_FILAS*MAX_COLUMNAS];
	private int escritoriosRetornados=0;
	//constructor
	public Aula(int newId,int newCantidadFilas,int newCantidadColumnas){
		id=newId;
		cantidadFilas=newCantidadFilas;
		cantidadColumnas=newCantidadColumnas;
		for(int i=0;i<cantidadFilas;i++){
			for(int j=0;j<cantidadColumnas;j++){
				escritorios[i][j]=false;
			}
		}
	}
	//getters
	public int getId(){
		return id;
	}
	public int getCantidadFilas(){
		return cantidadFilas;
	}
	public int getCantidadColumnas(){
		return cantidadColumnas;
	}
	//metodos internos
	private int escritoriosReales(){
		return escritoriosOcupados-escritoriosRetornados;
	}
	//metodos externos para validacion de datos
	public boolean puedoOcupar(){
		return escritoriosReales()<posicionesRetornadas.length;
	}
	public boolean puedoDesocupar(){
		return escritoriosReales()>0;
	}
	public boolean filaDesocupable(int fila){
		for(int j=0;j<cantidadColumnas;j++){
			if(escritorios[fila][j]){
				return true;
			}
		}
		return false;
	}
	public boolean escritorioOcupado(int fila,int columna){
		return escritorios[fila][columna];
	}
	//metodos externos para funcionalidades del menu
	public int ocuparEscritorio(){//Ocupar Aula
		int i,j,posicion;
		//si hay escritorios retornados, la posicion es la ultima retornada
		if(escritoriosRetornados>0){
			posicion=posicionesRetornadas[--escritoriosRetornados];
		}
		//sino, la posicion es la siguente en fila/columna
		else{
			posicion=escritoriosOcupados++;
		}
		//calcular indices
		i=posicion/cantidadFilas;
		j=posicion%cantidadFilas;
		//ocupar
		escritorios[i][j]=true;
		//retorno
		return posicion;
	}
	public void desocuparEscritorio(int fila,int columna){//Desocupar aula
		int posicion;
		//desocupo
		escritorios[fila][columna]=false;
		//si la posicion desocupada no es la ultima, la guardo
		posicion=fila*cantidadFilas+columna;
		if(posicion<escritoriosOcupados-1){
			posicionesRetornadas[escritoriosRetornados++]=posicion;
		}
		//sino, decremento escritorios ocupados
		else{
			escritoriosOcupados--;
		}
	}
	//formato de print
	@Override
	public String toString(){
		return "Id: "+id+", Capacidad: "+escritoriosReales()+"/"+cantidadFilas*cantidadColumnas+
				", Estado: "+((puedoOcupar())?"Hay espacio":"Esta llena");
	}
}