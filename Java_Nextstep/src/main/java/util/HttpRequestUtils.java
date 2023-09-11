package util;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpRequestUtils {

	private static final Logger log = LoggerFactory.getLogger(HttpRequestUtils.class);
	public static String getUrl(String firstLine) {
		String[] tokens = firstLine.split(" ");
		if (tokens.length != 3) return "First line error";
		String url = tokens[1];
		log.debug("request path : {}", url);
		// RequestHandler에서 구현하고 분리하기

		return url;
	}
	/**
	 * @param queryString은 URL에서 ? 이후에 전달되는 name=value 임
	 * @return
	 */
	public static Map<String, String> parseQueryString(String queryString) {
		if (Strings.isNullOrEmpty(queryString)) {
			return Maps.newHashMap();
		}
		
		String[] tokens = queryString.split("&");
		return Arrays.stream(tokens)
					.map(t -> getKeyValue(t, "="))
					.filter(p -> p != null)
					.collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));
	}
	
	static Pair getKeyValue(String keyValue, String regex) {
		if (Strings.isNullOrEmpty(keyValue)) {
			return null;
		}
		
		String[] tokens = keyValue.split(regex);
		if (tokens.length != 2) {
			return null;
		}
		
		return new Pair(tokens[0], tokens[1]);
	}
	
	public static Pair parseHeader(String header) {
		return getKeyValue(header, ": ");
	}
	
	public static class Pair {
		String key;
		String value;
		
		Pair(String key, String value) {
			this.key = key;
			this.value = value;
		}
		
		public String getKey() {
			return key;
		}
		
		public String getValue() {
			return value;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((key == null) ? 0 : key.hashCode());
			result = prime * result + ((value == null) ? 0 : value.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Pair other = (Pair) obj;
			if (key == null) {
				if (other.key != null)
					return false;
			} else if (!key.equals(other.key))
				return false;
			if (value == null) {
				if (other.value != null)
					return false;
			} else if (!value.equals(other.value))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "Pair [key=" + key + ", value=" + value + "]";
		}
	}
}
