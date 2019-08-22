package com.jkspad.tutorial;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.GdxRuntimeException;

/**
 * @author John Knight
 * Copyright http://www.jkspad.com
 *
 */
public class NdcTriangle extends ApplicationAdapter {
	private Mesh triangleMesh;
	private ShaderProgram shader;

	private final String VERTEX_SHADER = "attribute vec4 a_position;\n"
			+ "void main() {\n"
			+ " gl_Position = a_position;\n" +
			"}";

	private final String FRAGMENT_SHADER =
			"uniform vec4 u_color;\n"
					+ "void main() {\n"
					+ " gl_FragColor = u_color;\n"
					+ "}";

	protected void createMeshShader() {
		ShaderProgram.pedantic = false;
		shader = new ShaderProgram(VERTEX_SHADER, FRAGMENT_SHADER);
		String log = shader.getLog();
		if (!shader.isCompiled()){
			throw new GdxRuntimeException(log);
		}
		if (log!=null && log.length()!=0){
			Gdx.app.log("shader log", log);
		}
	}

	private void createTriangleMesh() {

		triangleMesh = new Mesh(true, 3, 0, new VertexAttribute(Usage.Position, 2,
				"a_position"));

		triangleMesh.setVertices(new float[] {
				0f, 0.5f,		// top vertex
				0.5f, -0.5f,    // bottom right vertex
				-0.5f, -0.5f 	// bottom left vertex
		});

	}

	@Override
	public void create() {
		createTriangleMesh();
		createMeshShader();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		shader.begin();
		shader.setUniformf("u_color", 1, 1, 1, 1);
		triangleMesh.render(shader, GL20.GL_TRIANGLES, 0, 3);
		shader.end();
	}

	@Override
	public void dispose() {
		super.dispose();
		shader.dispose();
		triangleMesh.dispose();
	}
}
