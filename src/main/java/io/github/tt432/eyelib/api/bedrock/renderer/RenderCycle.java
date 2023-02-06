package io.github.tt432.eyelib.api.bedrock.renderer;

/*
 * @author DerToaster98 Copyright (c) 30.03.2022 Developed by DerToaster98
 *         GitHub: https://github.com/DerToaster98
 *
 * Allows the end user to introduce custom render cycles
 */
public interface RenderCycle {
	String name();

	/**
	 * @author DerToaster98 Copyright (c) 30.03.2022 Developed by DerToaster98
	 *         GitHub: https://github.com/DerToaster98
	 */
	enum RenderCycleImpl implements RenderCycle {
		INITIAL, REPEATED, SPECIAL /* For special use by the user */
	}
}
