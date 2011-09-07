package org.anddev.andengine.opengl.shader;

import org.anddev.andengine.opengl.shader.exception.ShaderProgramLinkException;
import org.anddev.andengine.opengl.shader.util.constants.ShaderProgramConstants;
import org.anddev.andengine.opengl.util.GLState;
import org.anddev.andengine.opengl.vbo.VertexBufferObjectAttributes;

import android.opengl.GLES20;

/**
 * (c) Zynga 2011
 *
 * @author Nicolas Gramlich <ngramlich@zynga.com>
 * @since 13:56:44 - 25.08.2011
 */
public class PositionTextureCoordinatesPositionInterpolationTextureSelectShaderProgram extends ShaderProgram {
	// ===========================================================
	// Constants
	// ===========================================================

	private static PositionTextureCoordinatesPositionInterpolationTextureSelectShaderProgram INSTANCE;

	public static final String VERTEXSHADER =
			"uniform mat4 " + ShaderProgramConstants.UNIFORM_MODELVIEWPROJECTIONMATRIX + ";\n" +
			"uniform float " + ShaderProgramConstants.UNIFORM_POSITION_INTERPOLATION_MIX + ";\n" +
			"attribute vec4 " + ShaderProgramConstants.ATTRIBUTE_POSITION_0 + ";\n" +
			"attribute vec4 " + ShaderProgramConstants.ATTRIBUTE_POSITION_1 + ";\n" +
			"attribute vec2 " + ShaderProgramConstants.ATTRIBUTE_TEXTURECOORDINATES + ";\n" +
			"varying vec2 " + ShaderProgramConstants.VARYING_TEXTURECOORDINATES + ";\n" +
			"void main() {\n" +
			"	vec4 position = mix(" + ShaderProgramConstants.ATTRIBUTE_POSITION_0 + "," + ShaderProgramConstants.ATTRIBUTE_POSITION_1 + "," + ShaderProgramConstants.UNIFORM_POSITION_INTERPOLATION_MIX + ");\n" +
			"	" + ShaderProgramConstants.VARYING_TEXTURECOORDINATES + " = " + ShaderProgramConstants.ATTRIBUTE_TEXTURECOORDINATES + ";\n" +
			"	gl_Position = " + ShaderProgramConstants.UNIFORM_MODELVIEWPROJECTIONMATRIX + " * vec4(position.x, -position.y, 0, 1);\n" +
			"}";

	public static final String FRAGMENTSHADER = PositionTextureCoordinatesTextureSelectShaderProgram.FRAGMENTSHADER;

	// ===========================================================
	// Fields
	// ===========================================================

	public static int sUniformModelViewPositionMatrixLocation = ShaderProgram.LOCATION_INVALID;
	public static int sUniformTexture0Location = ShaderProgram.LOCATION_INVALID;
	public static int sUniformTexture1Location = ShaderProgram.LOCATION_INVALID;
	public static int sUniformTextureSelectTexture0Location = ShaderProgram.LOCATION_INVALID;
	public static int sUniformPositionInterpolationMixLocation = ShaderProgram.LOCATION_INVALID;

	// ===========================================================
	// Constructors
	// ===========================================================

	private PositionTextureCoordinatesPositionInterpolationTextureSelectShaderProgram() {
		super(PositionTextureCoordinatesPositionInterpolationTextureSelectShaderProgram.VERTEXSHADER, PositionTextureCoordinatesPositionInterpolationTextureSelectShaderProgram.FRAGMENTSHADER);
	}

	public static PositionTextureCoordinatesPositionInterpolationTextureSelectShaderProgram getInstance() {
		if(PositionTextureCoordinatesPositionInterpolationTextureSelectShaderProgram.INSTANCE == null) {
			PositionTextureCoordinatesPositionInterpolationTextureSelectShaderProgram.INSTANCE = new PositionTextureCoordinatesPositionInterpolationTextureSelectShaderProgram();
		}
		return PositionTextureCoordinatesPositionInterpolationTextureSelectShaderProgram.INSTANCE;
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	protected void link() throws ShaderProgramLinkException {
		GLES20.glBindAttribLocation(this.mProgramID, ShaderProgramConstants.ATTRIBUTE_POSITION_0_LOCATION, ShaderProgramConstants.ATTRIBUTE_POSITION_0);
		GLES20.glBindAttribLocation(this.mProgramID, ShaderProgramConstants.ATTRIBUTE_POSITION_1_LOCATION, ShaderProgramConstants.ATTRIBUTE_POSITION_1);
		GLES20.glBindAttribLocation(this.mProgramID, ShaderProgramConstants.ATTRIBUTE_TEXTURECOORDINATES_LOCATION, ShaderProgramConstants.ATTRIBUTE_TEXTURECOORDINATES);

		super.link();

		PositionTextureCoordinatesPositionInterpolationTextureSelectShaderProgram.sUniformModelViewPositionMatrixLocation = this.getUniformLocation(ShaderProgramConstants.UNIFORM_MODELVIEWPROJECTIONMATRIX);
		PositionTextureCoordinatesPositionInterpolationTextureSelectShaderProgram.sUniformTexture0Location = this.getUniformLocation(ShaderProgramConstants.UNIFORM_TEXTURE_0);
		PositionTextureCoordinatesPositionInterpolationTextureSelectShaderProgram.sUniformTexture1Location = this.getUniformLocation(ShaderProgramConstants.UNIFORM_TEXTURE_1);
		PositionTextureCoordinatesPositionInterpolationTextureSelectShaderProgram.sUniformTextureSelectTexture0Location = this.getUniformLocation(ShaderProgramConstants.UNIFORM_TEXTURESELECT_TEXTURE_0);
		PositionTextureCoordinatesPositionInterpolationTextureSelectShaderProgram.sUniformPositionInterpolationMixLocation = this.getUniformLocation(ShaderProgramConstants.UNIFORM_POSITION_INTERPOLATION_MIX);
	}

	@Override
	public void bind(final VertexBufferObjectAttributes pVertexBufferObjectAttributes) {
		GLES20.glDisableVertexAttribArray(ShaderProgramConstants.ATTRIBUTE_COLOR_LOCATION);
		GLES20.glDisableVertexAttribArray(ShaderProgramConstants.ATTRIBUTE_POSITION_LOCATION);
		GLES20.glEnableVertexAttribArray(ShaderProgramConstants.ATTRIBUTE_POSITION_0_LOCATION);
		GLES20.glEnableVertexAttribArray(ShaderProgramConstants.ATTRIBUTE_POSITION_1_LOCATION);

		super.bind(pVertexBufferObjectAttributes);

		GLES20.glUniformMatrix4fv(PositionTextureCoordinatesPositionInterpolationTextureSelectShaderProgram.sUniformModelViewPositionMatrixLocation, 1, false, GLState.getModelViewProjectionMatrix(), 0);
		GLES20.glUniform1i(PositionTextureCoordinatesPositionInterpolationTextureSelectShaderProgram.sUniformTexture0Location, 0);
		GLES20.glUniform1i(PositionTextureCoordinatesPositionInterpolationTextureSelectShaderProgram.sUniformTexture1Location, 1);
	}

	@Override
	public void unbind(final VertexBufferObjectAttributes pVertexBufferObjectAttributes) {
		super.unbind(pVertexBufferObjectAttributes);

		GLES20.glEnableVertexAttribArray(ShaderProgramConstants.ATTRIBUTE_COLOR_LOCATION);
		GLES20.glEnableVertexAttribArray(ShaderProgramConstants.ATTRIBUTE_POSITION_LOCATION);
		GLES20.glDisableVertexAttribArray(ShaderProgramConstants.ATTRIBUTE_POSITION_0_LOCATION);
		GLES20.glDisableVertexAttribArray(ShaderProgramConstants.ATTRIBUTE_POSITION_1_LOCATION);
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}