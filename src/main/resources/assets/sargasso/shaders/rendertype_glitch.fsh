#version 150

#moj_import <minecraft:fog.glsl>
#moj_import <minecraft:matrix.glsl>
#moj_import <minecraft:globals.glsl>

in vec4 texProj0;
in float sphericalVertexDistance;
in float cylindricalVertexDistance;

// https://www.shadertoy.com/view/3d3fR7
float tv_static(vec2 pos, float evolve) {
    float e = fract((evolve*0.01));

    float cx  = pos.x*e;
    float cy  = pos.y*e;
    
    return fract(23.0*fract(2.0/fract(fract(cx*2.4/cy*23.0)*fract(cx*evolve/pow(abs(cy),0.050)))));
}

out vec4 fragColor;

void main() {
    float scale = 1.1;
    vec2 coord = round(texProj0.xy / scale) * scale;
    vec3 color = vec3(tv_static(coord, GameTime));
    fragColor = apply_fog(vec4(color, 1.0), sphericalVertexDistance, cylindricalVertexDistance, FogEnvironmentalStart, FogEnvironmentalEnd, FogRenderDistanceStart, FogRenderDistanceEnd, FogColor);
}
