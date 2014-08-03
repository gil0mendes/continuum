package com.continuum.world.characters;

import com.continuum.datastructures.AABB;
import com.continuum.main.Configuration;
import com.continuum.main.Continuum;
import com.continuum.rendering.TextureManager;
import com.continuum.utilities.FastRandom;
import com.continuum.world.World;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

import static org.lwjgl.opengl.GL11.*;

public final class Slime extends Character {

    public static int _instanceCounter;

    private FastRandom _rand = new FastRandom(Continuum.getInstance().getTime() + _instanceCounter);
    private long _lastChangeOfDirectionAt = Continuum.getInstance().getTime();
    private Vector3f _movementTarget = new Vector3f();

    public Slime(World parent) {
        super(parent, Configuration.getSettingNumeric("WALKING_SPEED") / 4, Configuration.getSettingNumeric("RUNNING_FACTOR"), Configuration.getSettingNumeric("JUMP_INTENSITY"));
        _instanceCounter++;
    }

    public void update() {
        super.update();
    }

    public void render() {
        super.render();

        glPushMatrix();

        glTranslatef(getPosition().x, getPosition().y, getPosition().z);
        glRotatef((float) _yaw, 0f, 1f, 0f);

        glEnable(GL_TEXTURE_2D);
        TextureManager.getInstance().bindTexture("slime");

        float brightness = (float) Math.pow(0.84, 15.0 - _parent.getDaylight() * 15.0);

        glBegin(GL_QUADS);
        GL11.glColor3f(brightness, brightness, brightness);

        // TOP
        GL11.glTexCoord2f(0f / 64f, 28f / 32f);
        GL11.glVertex3f(-0.25f, 0.2f, 0.25f);
        GL11.glTexCoord2f(6f / 64f, 28f / 32f);
        GL11.glVertex3f(0.25f, 0.2f, 0.25f);
        GL11.glTexCoord2f(6f / 64f, 22f / 32f);
        GL11.glVertex3f(0.25f, 0.2f, -0.25f);
        GL11.glTexCoord2f(0f / 64f, 22f / 32f);
        GL11.glVertex3f(-0.25f, 0.2f, -0.25f);

        // LEFT
        GL11.glTexCoord2f(0f / 64f, 28f / 32f);
        GL11.glVertex3f(-0.25f, -0.2f, -0.25f);
        GL11.glTexCoord2f(6f / 64f, 28f / 32f);
        GL11.glVertex3f(-0.25f, -0.2f, 0.25f);
        GL11.glTexCoord2f(6f / 64f, 22f / 32f);
        GL11.glVertex3f(-0.25f, 0.2f, 0.25f);
        GL11.glTexCoord2f(0f / 64f, 22f / 32f);
        GL11.glVertex3f(-0.25f, 0.2f, -0.25f);


        // BACK
        GL11.glTexCoord2f(0f / 64f, 28f / 32f);
        GL11.glVertex3f(-0.25f, -0.2f, 0.25f);
        GL11.glTexCoord2f(6f / 64f, 28f / 32f);
        GL11.glVertex3f(0.25f, -0.2f, 0.25f);
        GL11.glTexCoord2f(6f / 64f, 22f / 32f);
        GL11.glVertex3f(0.25f, 0.2f, 0.25f);
        GL11.glTexCoord2f(0f / 64f, 22f / 32f);
        GL11.glVertex3f(-0.25f, 0.2f, 0.25f);

        // RIGHT
        GL11.glTexCoord2f(0f / 64f, 28f / 32f);
        GL11.glVertex3f(0.25f, 0.2f, -0.25f);
        GL11.glTexCoord2f(6f / 64f, 28f / 32f);
        GL11.glVertex3f(0.25f, 0.2f, 0.25f);
        GL11.glTexCoord2f(6f / 64f, 22f / 32f);
        GL11.glVertex3f(0.25f, -0.2f, 0.25f);
        GL11.glTexCoord2f(0f / 64f, 22f / 32f);
        GL11.glVertex3f(0.25f, -0.2f, -0.25f);

        GL11.glColor3f(brightness * 0.25f, brightness * 0.25f, brightness * 0.25f);

        // FRONT
        GL11.glTexCoord2f(0f / 64f, 28f / 32f);
        GL11.glVertex3f(-0.25f, 0.2f, -0.25f);
        GL11.glTexCoord2f(6f / 64f, 28f / 32f);
        GL11.glVertex3f(0.25f, 0.2f, -0.25f);
        GL11.glTexCoord2f(6f / 64f, 22f / 32f);
        GL11.glVertex3f(0.25f, -0.2f, -0.25f);
        GL11.glTexCoord2f(0f / 64f, 22f / 32f);
        GL11.glVertex3f(-0.25f, -0.2f, -0.25f);

        // BOTTOM
        GL11.glTexCoord2f(0f / 64f, 28f / 32f);
        GL11.glVertex3f(-0.25f, -0.2f, -0.25f);
        GL11.glTexCoord2f(6f / 64f, 28f / 32f);
        GL11.glVertex3f(0.25f, -0.2f, -0.25f);
        GL11.glTexCoord2f(6f / 64f, 22f / 32f);
        GL11.glVertex3f(0.25f, -0.2f, 0.25f);
        GL11.glTexCoord2f(0f / 64f, 22f / 32f);
        GL11.glVertex3f(-0.25f, -0.2f, 0.25f);

        GL11.glEnd();

        glDisable(GL11.GL_TEXTURE_2D);

        glPopMatrix();
    }

    public void updatePosition() {
        super.updatePosition();
    }

    public void processMovement() {
        double distanceToPlayer = distanceSquaredTo(_parent.getPlayer().getPosition());

        if (distanceToPlayer > 5 && distanceToPlayer < 32) {
            _movementTarget.set(_parent.getPlayer().getPosition());
        }

        if (Continuum.getInstance().getTime() - _lastChangeOfDirectionAt > 5000 || distanceToPlayer <= 5) {
            _movementTarget.set((float) (getPosition().x + _rand.randomDouble() * 500), getPosition().y, (float) (getPosition().z + _rand.randomDouble() * 500));
            _lastChangeOfDirectionAt = Continuum.getInstance().getTime();
        }


        lookAt(_movementTarget);
        walkForward();

        if (_rand.randomDouble() < -0.94)
            jump();
    }

    protected AABB generateAABBForPosition(Vector3f p) {
        return new AABB(p, new Vector3f(.25f, .25f, .25f));
    }

    public AABB getAABB() {
        return generateAABBForPosition(getPosition());
    }

    @Override
    protected void handleVerticalCollision() {
        // Do nothing
    }

    @Override
    protected void handleHorizontalCollision() {
        _lastChangeOfDirectionAt = 0;
    }
}