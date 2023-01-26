package org.wsj;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.sun.net.httpserver.Headers;

public class Header {

	private Map<String, List<String>> map;
	
	public Header(Headers headers) {
		map = headers;
	}
	
    /**
     * Normalize the key by converting to following form.
     * First {@code char} upper case, rest lower case.
     * key is presumed to be {@code ASCII}.
     */
    private String normalize (String key) {
        if (key == null) {
            return null;
        }
        int len = key.length();
        if (len == 0) {
            return key;
        }
        char[] b = key.toCharArray();
        if (b[0] >= 'a' && b[0] <= 'z') {
            b[0] = (char)(b[0] - ('a' - 'A'));
        } else if (b[0] == '\r' || b[0] == '\n')
            throw new IllegalArgumentException("illegal character in key");

        for (int i=1; i<len; i++) {
            if (b[i] >= 'A' && b[i] <= 'Z') {
                b[i] = (char) (b[i] + ('a' - 'A'));
            } else if (b[i] == '\r' || b[i] == '\n')
                throw new IllegalArgumentException("illegal character in key");
        }
        return new String(b);
    }

    public int size() {return map.size();}

    public boolean isEmpty() {return map.isEmpty();}

    public boolean containsKey(Object key) {
        if (key == null) {
            return false;
        }
        if (!(key instanceof String)) {
            return false;
        }
        return map.containsKey (normalize((String)key));
    }

    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    public List<String> get(Object key) {
        return map.get(normalize((String)key));
    }

    /**
     * Returns the first value from the {@link List} of {@code String}
     * values for the given key (if at least one exists).
     *
     * @param key the key to search for
     * @return the first {@code String} value associated with the key
     */
    public String getFirst (String key) {
        List<String> l = map.get(normalize(key));
        if (l == null) {
            return null;
        }
        return l.get(0);
    }

    public List<String> put(String key, List<String> value) {
        for (String v : value)
            checkValue(v);
        return map.put (normalize(key), value);
    }

    /**
     * Adds the given value to the list of headers for the given key. If
     * the mapping does not already exist, then it is created.
     *
     * @param key the header name
     * @param value the value to add to the header
     */
    public void add (String key, String value) {
        checkValue(value);
        String k = normalize(key);
        List<String> l = map.get(k);
        if (l == null) {
            l = new LinkedList<String>();
            map.put(k,l);
        }
        l.add (value);
    }

    private static void checkValue(String value) {
        int len = value.length();
        for (int i=0; i<len; i++) {
            char c = value.charAt(i);
            if (c == '\r') {
                // is allowed if it is followed by \n and a whitespace char
                if (i >= len - 2) {
                    throw new IllegalArgumentException("Illegal CR found in header");
                }
                char c1 = value.charAt(i+1);
                char c2 = value.charAt(i+2);
                if (c1 != '\n') {
                    throw new IllegalArgumentException("Illegal char found after CR in header");
                }
                if (c2 != ' ' && c2 != '\t') {
                    throw new IllegalArgumentException("No whitespace found after CRLF in header");
                }
                i+=2;
            } else if (c == '\n') {
                throw new IllegalArgumentException("Illegal LF found in header");
            }
        }
    }

    /**
     * Sets the given value as the sole header value for the given
     * key. If the mapping does not already exist, then it is created.
     *
     * @param key the header name
     * @param value the header value to set
     */
    public void set (String key, String value) {
        LinkedList<String> l = new LinkedList<String>();
        l.add (value);
        put (key, l);
    }


    public List<String> remove(Object key) {
        return map.remove(normalize((String)key));
    }

    public void putAll(Map<? extends String,? extends List<String>> t)  {
        map.putAll (t);
    }

    public void clear() {map.clear();}

    public Set<String> keySet() {return map.keySet();}

    public Collection<List<String>> values() {return map.values();}

    public Set<Map.Entry<String, List<String>>> entrySet() {
        return map.entrySet();
    }

    public boolean equals(Object o) {return map.equals(o);}

    public int hashCode() {return map.hashCode();}
}
