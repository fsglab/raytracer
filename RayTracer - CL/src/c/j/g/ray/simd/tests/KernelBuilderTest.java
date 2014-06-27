package c.j.g.ray.simd.tests;

import c.j.g.ray.simd.source.SourceCreater;
import c.j.g.ray.simd.source.SourceFinal;
import c.j.g.ray.simd.source.SourcePart;

public class KernelBuilderTest {

	@Override
	public String toString() {
		return String.format("KernelBuilderTest [val=%s]", val);
	}

	@SourcePart({"//kernel","Line 1"})
	private static native void kernel(byte[] b, short s);

	@SourcePart({"//kernel2", "Line 2{", "3  //v.a.l. will be replaced by '5'.","4  v.a.l. = 'val'", "} end line 5" })
	private native void kernel2(KernelBuilderTest test);

	@SourceFinal
	private final int val = 5;

	@SourcePart("//main")
	public static void main(String... s) {
		System.setProperty("debug", "true");
		System.out.println(new SourceCreater(new KernelBuilderTest()).getCode());
	}

}
