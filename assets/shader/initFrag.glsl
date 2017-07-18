#version 150 core
out vec4 out_color;

uniform sampler2D s_texture;

in vec2 v_tex_coord;
in vec3 v_color;

void main() {
    vec4 tex_color = texture(s_texture, v_tex_coord);
    if(tex_color.a == 0.0) // Discard transparent pixels.
        discard;
    out_color = tex_color * vec4(v_color, 1.0);
}
