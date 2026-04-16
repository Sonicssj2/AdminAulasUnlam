public class Aula{
	//atributos
	private int id;
	private boolean[][]escritorios;
	private int cantidadFilas;
	private int cantidadColumnas;
	private int escritoriosOcupados=0;
	private int[]posicionesRetornadas;
	private int escritoriosRetornados=0;
	//constructor
	Aula(int id,int cantidadFilas,int cantidadColumnas){
		this.id=id;
		this.cantidadFilas=cantidadFilas;
		this.cantidadColumnas=cantidadColumnas;
		escritorios=new boolean[cantidadFilas][cantidadColumnas];
		posicionesRetornadas=new int[cantidadFilas*cantidadColumnas];
	}
	//getters
	public int getId(){
		return id;
	}
	//otros metodos
	public boolean estaLlena(){
		return escritoriosOcupados==cantidadFilas*cantidadColumnas;
	}
	public int[]ocuparEscritorio(){
		int[]posicionOcupar={-1,-1};
		int i,j,pos;
		//si no hay espacio
		if(estaLlena()){
			System.out.println("No se ocupó un escritorio: El aula esta llena!");
			return posicionOcupar;
		}
		//si hay escritorios retornados
		if(escritoriosRetornados>0){
			//la posicion es la ultima retornada
			pos=posicionesRetornadas[--escritoriosRetornados];
		}
		//sino
		else{
			//la posicion es la siguente en fila/columna
			pos=escritoriosOcupados;
		}
		//calcular indices
		i=pos/cantidadFilas;
		j=pos%cantidadFilas;
		//ocupar
		escritorios[i][j]=true;
		escritoriosOcupados++;
		posicionOcupar[0]=i;
		posicionOcupar[1]=j;
		//retorno
		System.out.println("Se ocupó un escritorio correctamente.");
		return posicionOcupar;
	}
	public boolean desocuparEscritorio(int fila,int columna){
		int pos;
		//si ya estaba desocupado
		if(!escritorios[fila][columna]){
			System.out.println("No se desocupo el escritorio: Ya estaba desocupado!");
			return false;
		}
		//desocupo
		escritorios[fila][columna]=false;
		escritoriosOcupados--;
		//si la posicion desocupada no es la ultima, la guardo
		pos=fila*cantidadFilas+columna;
		if(pos!=escritoriosOcupados){
			posicionesRetornadas[escritoriosRetornados++]=fila*cantidadFilas+columna;
		}
		//retorno
		System.out.println("Se desocupo el escritorio correctamente.");
		return true;
	}
	//formato de print
	@Override
	public String toString(){
		return "Id: "+id+", Capacidad: "+cantidadFilas*cantidadColumnas+
				", Estado: "+((estaLlena())?"Esta llena":"Hay espacio");
	}
}