package com.continuum.rendering;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.ARBVertexBufferObject;
import org.lwjgl.opengl.GLContext;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class VBOHelper {

    private static VBOHelper _instance = null;

    public static VBOHelper getInstance() {
        if (_instance == null) {
            _instance = new VBOHelper();
        }

        return _instance;
    }


    public int createVboId() {
        if (GLContext.getCapabilities().GL_ARB_vertex_buffer_object) {
            IntBuffer buffer = BufferUtils.createIntBuffer(1);
            ARBVertexBufferObject.glGenBuffersARB(buffer);
            return buffer.get(0);
        }
        return 0;
    }

    public void bufferVboData(int id, FloatBuffer buffer, int drawMode) {
        if (GLContext.getCapabilities().GL_ARB_vertex_buffer_object) {
            ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, id);
            ARBVertexBufferObject.glBufferDataARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, buffer, drawMode);
            ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, 0);
        }
    }

    public void bufferVboElementData(int id, IntBuffer buffer, int drawMode) {
        if (GLContext.getCapabilities().GL_ARB_vertex_buffer_object) {
            ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ELEMENT_ARRAY_BUFFER_ARB, id);
            ARBVertexBufferObject.glBufferDataARB(ARBVertexBufferObject.GL_ELEMENT_ARRAY_BUFFER_ARB, buffer, drawMode);
            ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ELEMENT_ARRAY_BUFFER_ARB, 0);
        }
    }

}