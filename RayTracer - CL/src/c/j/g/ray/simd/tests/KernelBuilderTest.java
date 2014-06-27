package c.j.g.ray.simd.tests;

import c.j.g.ray.simd.kernel.KernelCreater;
import c.j.g.ray.simd.kernel.KernelFinal;
import c.j.g.ray.simd.kernel.KernelPart;

public class KernelBuilderTest {

	@Override
	public String toString() {
		return String.format("KernelBuilderTest [val=%s]", val);
	}

	@KernelPart({"//kernel","Line 1"})
	private native void kernel();

	@KernelPart({"//kernel2", "Line 2{", "3  //v.a.l. will be replaced by '5'.","4  v.a.l. = 'val'", "} end line 5" })
	private native void kernel2();

	@KernelFinal
	private final int val = 5;

	@KernelPart("//main")
	public static void main(String... s) {
		System.setProperty("debug", "true");
		System.out.println(new KernelCreater(new KernelBuilderTest()).getCode());
	}

}
