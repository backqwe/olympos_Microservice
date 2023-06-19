package org.olympos.athena.cache;

public class CacheResult<T> {

	private boolean succ;
	private String code;
	private T module;

	public static <T> CacheResult<T> of(boolean succ) {
		CacheResult<T> result = new CacheResult<T>();
		result.setSucc(succ);
		return result;
	}
	public static <T> CacheResult<T> of(boolean succ, T t) {
		CacheResult<T> result = new CacheResult<T>();
		result.setModule(t);
		result.setSucc(succ);
		return result;
	}

	public static <T> CacheResult<T> of(String code) {
		CacheResult<T> result = new CacheResult<T>();
		result.setCode(code);
		return result;
	}
	
	public boolean isEmpty(){
		if(this.module == null){
			return true;
		}
		if("".equals(this.module.toString().trim())){
			return true;
		}
		return false;
	}

	public boolean isSucc() {
		return succ;
	}

	public void setSucc(boolean succ) {
		this.succ = succ;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public T getModule() {
		return module;
	}

	public void setModule(T module) {
		this.module = module;
	}

}
