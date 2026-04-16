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
				posicionesRetornadas[i*cantidadFilas+j]=0;
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
	//metodos externos para validacion de datos
	public boolean puedoOcupar(){
		return escritoriosOcupados<posicionesRetornadas.length;
	}
	public boolean puedoDesocupar(){
		return escritoriosOcupados>0;
	}
	public boolean escritorioOcupado(int fila,int columna){
		return escritorios[fila][columna];
	}
	//metodos externos para funcionalidades del menu
	public int ocuparEscritorio(){
		int i,j,posicion;
		//si hay escritorios retornados
		if(escritoriosRetornados>0){
			//la posicion es la ultima retornada
			posicion=posicionesRetornadas[--escritoriosRetornados];
		}
		//sino
		else{
			//la posicion es la siguente en fila/columna
			posicion=escritoriosOcupados;
		}
		//calcular indices
		i=posicion/cantidadFilas;
		j=posicion%cantidadFilas;
		//ocupar
		escritorios[i][j]=true;
		escritoriosOcupados++;
		//retorno
		return posicion;
	}
	public void desocuparEscritorio(int fila,int columna){
		int posicion;
		//desocupo
		escritorios[fila][columna]=false;
		escritoriosOcupados--;
		//si la posicion desocupada no es la ultima, la guardo
		posicion=fila*cantidadFilas+columna;
		if(posicion!=escritoriosOcupados){
			posicionesRetornadas[escritoriosRetornados++]=fila*cantidadFilas+columna;
		}
	}
	//formato de print
	@Override
	public String toString(){
		return "Id: "+id+", Capacidad: "+cantidadFilas*cantidadColumnas+
				", Estado: "+((puedoOcupar())?"Hay espacio":"Esta llena");
	}
}