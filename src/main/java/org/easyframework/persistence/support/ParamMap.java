/**
 * 
 */
package org.easyframework.persistence.support;

import java.util.HashMap;

/**
 * 参数构建Map
 * @author zhoupuyue
 * @date 2013-9-17
 */
public class ParamMap<K,V> extends HashMap<K, V> {

	private static final long serialVersionUID = 2733972560918515350L;
	
	public ParamMap<K,V> map(K key, V value){
		super.put(key, value);
		return this;
	}

}
