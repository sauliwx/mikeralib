package mikera.net;

import java.io.IOException;
import java.io.OutputStream;

public class DataOutputStream extends OutputStream {
	private Data data;
	
	public DataOutputStream() {
		this(new Data());
	}
	
	public DataOutputStream(Data d) {
		data=d;
	}
	
	@Override
	public void write(int arg0) {
		data.append((byte)arg0);
	}
	
	@Override
	public void write(byte[] bytes) {
		data.append(bytes, 0, bytes.length);
	}
	
	@Override
	public void write(byte[] bytes, int offset, int length) {
		data.append(bytes, offset, length);
	}
	
	public Data getData() {
		return data;
	}
	
	public Data getCopyOfData() {
		return data.clone();
	}
}