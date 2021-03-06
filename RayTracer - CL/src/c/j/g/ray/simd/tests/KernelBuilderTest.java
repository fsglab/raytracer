package c.j.g.ray.simd.tests;

import c.j.g.ray.simd.source.SourceCreator;
import c.j.g.ray.simd.source.SourceFinal;
import c.j.g.ray.simd.source.SourceInclude;
import c.j.g.ray.simd.source.SourcePart;

@SourceInclude(KernelBuilderTest.Include.class)
public class KernelBuilderTest {

	@SourcePart({ "//kernel", "Line 1" })
	private static native void kernel(byte[] b, short s);

	@SourcePart("//main")
	public static void main(String... s) {
		System.setProperty("debug", "true");
		System.out
				.println(new SourceCreator(new KernelBuilderTest()).getCode());
	}

	@SourceFinal
	private final int val = 5;

	@SourcePart({ "//kernel2", "Line 2{",
			"3  //v.a.l. will be replaced by '5'.", "4  v.a.l. = 'val'",
			"} end line 5" })
	private native void kernel2(KernelBuilderTest test);

	@Override
	public String toString() {
		return String.format("KernelBuilderTest [val=%s]", val);
	}

	@SourcePart({"a = 1;", "b = 2;"})
	public static  interface Include{
		
	}
	
}
