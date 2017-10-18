package com.horstmann.violet.application.gui.util.chenzuo.Bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Time  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2040523252454106582L;
	
    //original limit
    String original;

    //map<processName,ti,realTime>
    Map<String, Pair<String, Double>> mapping;

    //error list
    List<String> error;

    //result<uninquery,realTime,isOk>
    Map<String, Pair<String, String>> showMap;

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public Map<String, Pair<String, Double>> getMapping() {
        return mapping;
    }

    public void setMapping(String tmp) {
        mapping = new HashMap<String, Pair<String, Double>>();
        String[] s = tmp.split(",");
        for (String str : s) {
            String[] t = str.split("\\&");
            mapping.put(t[1], new Pair(t[0], Double.parseDouble(t[2])));
        }
    }

    public List<String> getError() {
        return error;
    }

    public void setError(String tmp) {
        error = new ArrayList<String>();
        String[] s = tmp.split(",");
        for (String str : s) {
            error.add(str);
        }
    }

    public Map<String, Pair<String, String>> getShowMap() {
        return showMap;
    }

    public void setShowMap() {
        showMap = new HashMap<String, Pair<String, String>>();
        String[] oStrings = original.split(",");
        for (String t : oStrings) {
            showMap.put(t, values(t));
        }
    }

    public Pair values(String tmp) {
        String symbol = "";
        if (tmp.contains("=")) {
            if (tmp.contains("<")) {
                symbol = "<=";
            } else if (tmp.contains(">")) {
                symbol = ">=";
            } else {
                symbol = "=";
            }
        } else if (tmp.contains(">")) {
            symbol = ">";
        } else {
            symbol = "<";
        }
        String[] sAndV = tmp.split(symbol), keys = null;
        String pV = sAndV[1], str = sAndV[0], result = "";
        double v = 0d, value = 0d;
        boolean r = false, flag = true;
        if (str.contains("+")) {
            keys = str.split("\\+");
        } else {
            keys = new String[1];
            keys[0] = str;
        }
        for (String key : keys) {
        	if(mapping.get(key)!=null){
        		v = (double) mapping.get(key).getSecond();
                if (v == 300d)
                    flag = false;
                result += key + "=" + v + " ";
                value += v;
        	}
        }
        if (flag)
            r = charge(symbol, value, Double.parseDouble(pV));
        return new Pair(result, r);
    }

    boolean charge(String symbol, double a, double b) {
        boolean r = true;
        if (symbol.equals("=")) {
            r = a == b;
        }
        if (symbol.equals("<=")) {
            r = a <= b;
        }
        if (symbol.equals(">=")) {
            r = a >= b;
        }
        if (symbol.equals("<")) {
            r = a < b;
        }
        if (symbol.equals(">")) {
            r = a > b;
        }
        return r;
    }
}
