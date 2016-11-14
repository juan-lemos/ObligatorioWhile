package examples.while_ut1.ast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CheckState {

	public Map <String,ObjectState>mapa=new HashMap<String,ObjectState>(); //nombre varible definida no valor
	public ArrayList<String> errores=new ArrayList<String>();


	public static Map<String,ObjectState> clonarMapa(Map<String,ObjectState> mapaOriginal){
		Map <String,ObjectState>mapaClonado=new HashMap<String,ObjectState>();

		Iterator<String> it = mapaOriginal.keySet().iterator();
		while(it.hasNext()){
			String key = it.next();
			ObjectState osClone=mapaOriginal.get(key).clone();
			mapaClonado.put(key, osClone);
		}
		return mapaClonado;
	}

	static public CheckState intersect(final CheckState first, final CheckState second) {
		CheckState result = new CheckState();
		for (String id : first.mapa.keySet()) {
			ObjectState firstEntry = first.mapa.get(id);
			ObjectState secondEntry=null;
			if (second.mapa.containsKey(id)){
				secondEntry= second.mapa.get(id);
			}
			if (secondEntry!=null && firstEntry.tipo.equals(secondEntry.tipo)) {
				result.mapa.put(id, firstEntry);
			}
		}
		return result;
	}

	static public void printErrorList(CheckState checkState){
		for (String error:checkState.errores){
			System.out.println(error);
		}

	}
}
