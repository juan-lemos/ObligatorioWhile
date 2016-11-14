package examples.while_ut1.ast;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class State {
    public Map <String,Object> mapaValores = new HashMap<String,Object>();
    public Map <String,String> mapaTipo = new HashMap<String,String>();
    
    public String toString(){
        String rest="";
        for (Map.Entry<String, Object> entry : mapaValores.entrySet())
        {
            rest += (entry.getKey() + " = " + entry.getValue()+"\n");
        }

        return rest;
    }

	@Override
	public boolean equals(Object arg0) {

		return super.equals(arg0);
	}
    
    
    public State clone() {
    	State state=new State();
    	state.mapaValores=clonarMapaValores(this.mapaValores);
    	state.mapaTipo=clonarMapaTipo(this.mapaTipo);
    	return state;
    }
    
    public static Map<String,Object> clonarMapaValores(Map<String,Object> mapaOriginal){
		Map <String,Object>mapaClonado=new HashMap<String,Object>();
		Iterator<String> it = mapaOriginal.keySet().iterator();
		while(it.hasNext()){
			String key = it.next();
			mapaClonado.put(key, mapaOriginal.get(key));
		}
		return mapaClonado;
	}
    
    public static Map<String,String> clonarMapaTipo(Map<String,String> mapaOriginal){
		Map <String,String>mapaClonado=new HashMap<String,String>();
		Iterator<String> it = mapaOriginal.keySet().iterator();
		while(it.hasNext()){
			String key = it.next();
			String osClone=new String(mapaOriginal.get(key));
			mapaClonado.put(key, osClone);
		}
		return mapaClonado;
	}
    
    public static Map<String,Object> actualizarValoresMapViejo(Map<String,Object> mapaViejo,Map<String,Object>mapaNuevo){
		Iterator<String> it = mapaViejo.keySet().iterator();
		while(it.hasNext()){
			String key = it.next();
			mapaViejo.put(key, mapaNuevo.get(key));
		}
		return mapaViejo;
    	
    }
    
    
    
}