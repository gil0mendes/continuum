#version 150 core
uniform mat4 u_projection, u_view;

in vec2 at_tex_coord;
in vec3 at_color, at_position;

out vec2 v_tex_coord;
out vec3 v_color;

void main() {
    v_tex_coord = at_tex_coord;
    v_color = at_color;
    gl_Position = u_projection * u_view * vec4(at_position, 1.0);
}
