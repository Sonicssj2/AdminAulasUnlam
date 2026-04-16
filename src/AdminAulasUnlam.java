public class AdminAulasUnlam{
	//constantes
	public static final int MAX_AULAS=10;
	//atributos
	private Aula[]aulas=new Aula[MAX_AULAS];
	private int cantidadAulas=0;
	private int[]indexAulasEliminadas=new int[MAX_AULAS];
	private int cantidadAulasEliminadas=0;
	//metodos internos
	private int buscarIndexAula(int id){
		int tope=cantidadAulas+cantidadAulasEliminadas;
		Aula aula;
		for(int i=0;i<tope;i++){
			aula=aulas[i];
			if(aula!=null&&aula.getId()==id){
				return i;
			}
		}
		return -1;
	}
	//metodos externos para validacion de datos
	public boolean pudedoAgregarAula(){
		return cantidadAulas<aulas.length;
	}
	public boolean hayAulas(){
		return cantidadAulas>0;
	}
	public boolean existeAula(int id){
		return buscarIndexAula(id)!=-1;
	}
	public boolean puedoOcuparAula(int id){
		return aulas[buscarIndexAula(id)].puedoOcupar();
	}
	public boolean puedoDesocuparAula(int id){
		return aulas[buscarIndexAula(id)].puedoDesocupar();
	}
	public int getCantitdadFilasAula(int id){
		return aulas[buscarIndexAula(id)].getCantidadFilas();
	}
	public int getCantitdadColumnasAula(int id){
		return aulas[buscarIndexAula(id)].getCantidadColumnas();
	}
	public boolean escritorioOcupadoAula(int id,int fila,int columa){
		return aulas[buscarIndexAula(id)].escritorioOcupado(fila,columa);
	}
	//metodos externos para funcionalidades del menu
	public String[]formatearAulas(){//Listar aulas
		String[]aulasFormateadas=new String[cantidadAulas];
		Aula aula;
		for(int i=0;i<cantidadAulas+cantidadAulasEliminadas;i++){
			aula=aulas[i];
			if(aula!=null){
				aulasFormateadas[i]=aula.toString();
			}
		}
		return aulasFormateadas;
	}
	public void agregarAula(int id,int cantidadFilas,int cantidadColumnas){//Agregar aula
		int pos;
		//si hay posiciones libres, la posicion es la ultima liberada
		if(cantidadAulasEliminadas>0){
			pos=indexAulasEliminadas[--cantidadAulasEliminadas];
		}
		//sino, la posicion es la siguente en fila
		else{
			pos=cantidadAulas++;
		}
		//agregar
		aulas[pos]=new Aula(id,cantidadFilas,cantidadColumnas);
	}
	public String formatearAula(int id){//Estado de aula
		return aulas[buscarIndexAula(id)].toString();
	}
	public void eliminarAula(int id){//Eliminar aula
		int index=buscarIndexAula(id);
		//elimino
		aulas[index]=null;
		cantidadAulas--;
		//si la posicion desocupada no es la ultima, la guardo
		if(index!=cantidadAulas){
			indexAulasEliminadas[cantidadAulasEliminadas++]=index;
		}
	}
	public int ocuparAula(int id){
		return aulas[buscarIndexAula(id)].ocuparEscritorio();
	}
	public void desocuparAula(int id,int fila, int columna){
		aulas[buscarIndexAula(id)].desocuparEscritorio(fila,columna);
	}
}