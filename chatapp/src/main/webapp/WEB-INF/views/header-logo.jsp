<%@ page contentType="text/html; charset=UTF-8" %>
<!-- =================================== -->
<!-- 共通ヘッダーロゴコンポーネント -->
<!-- =================================== -->
<div class="header-logo">
    <svg viewBox="0 0 800 120" xmlns="http://www.w3.org/2000/svg" style="height: 80px;"
        <!-- Background circles -->
        <circle cx="40" cy="60" r="10" fill="#FF8B20" opacity="0.7"/>
        <circle cx="750" cy="30" r="8" fill="#333" opacity="0.8"/>
        <circle cx="720" cy="90" r="6" fill="#FF8B20" opacity="0.5"/>
        
        <!-- Main JB icon container -->
        <g transform="translate(15, 15)">
            <!-- Outer frame -->
            <path d="M0 15 L0 0 L30 0 L45 15 L45 60 L60 75 L60 105 L30 105 L15 90 L15 75 L0 60 Z" 
                  fill="none" stroke="#FF8B20" stroke-width="6" stroke-linejoin="round"/>
            
            <!-- Inner frame -->
            <path d="M10 25 L10 15 L25 15 L35 25 L35 55 L45 65 L45 90 L25 90 L15 80 L15 65 L10 60 Z" 
                  fill="#FF8B20" opacity="0.2"/>
            
            <!-- J letter -->
            <path d="M18 30 L18 65 Q18 75 25 75 Q32 75 32 65 L32 58" 
                  fill="none" stroke="white" stroke-width="4" stroke-linecap="round"/>
            
            <!-- B letter -->
            <path d="M40 30 L40 80 M40 30 L50 30 Q54 30 54 38 Q54 45 50 45 L40 45 M40 45 L52 45 Q56 45 56 53 Q56 60 52 60 L40 60" 
                  fill="none" stroke="white" stroke-width="3" stroke-linecap="round"/>
            
            <!-- Decorative arc -->
            <path d="M8 85 Q30 98 52 85" fill="none" stroke="#B8860B" stroke-width="4" opacity="0.8"/>
        </g>
        
        <!-- jobridge text -->
        <g transform="translate(140, 60)" fill="#333" font-family="Arial, sans-serif" font-weight="bold">
            <!-- jo -->
            <text x="0" y="15" font-size="36" fill="#333">jo</text>
            <!-- bridge with stylized 'r' -->
            <text x="45" y="15" font-size="36" fill="#FF8B20">b</text>
            <text x="70" y="15" font-size="36" fill="#FF8B20">r</text>
            <text x="90" y="15" font-size="36" fill="#333">idge</text>
        </g>
        
        <!-- chat text -->
        <g transform="translate(380, 60)" fill="#333" font-family="Arial, sans-serif" font-weight="bold">
            <!-- ch -->
            <text x="0" y="15" font-size="36" fill="#333">ch</text>
            <!-- at with stylized 'a' -->
            <text x="60" y="15" font-size="36" fill="#FF8B20">a</text>
            <text x="85" y="15" font-size="36" fill="#333">t</text>
        </g>
        
        <!-- Chat bubble decorations -->
        <g transform="translate(550, 20)">
            <!-- Large bubble -->
            <ellipse cx="25" cy="25" rx="28" ry="20" fill="#FF8B20" opacity="0.3"/>
            <ellipse cx="25" cy="25" rx="28" ry="20" fill="none" stroke="#FF8B20" stroke-width="2"/>
            
            <!-- Small bubble dots -->
            <circle cx="8" cy="40" r="3" fill="#FF8B20"/>
            <circle cx="16" cy="44" r="2" fill="#FF8B20" opacity="0.7"/>
            <circle cx="4" cy="48" r="1.5" fill="#FF8B20" opacity="0.5"/>
        </g>
        
        <!-- Connection lines -->
        <g stroke="#FF8B20" stroke-width="1.5" opacity="0.4">
            <path d="M120 50 Q200 35 280 50" fill="none"/>
            <path d="M360 75 Q420 85 480 75" fill="none"/>
        </g>
        
        <!-- Additional decorative elements -->
        <g transform="translate(680, 60)">
            <!-- Network nodes -->
            <circle cx="0" cy="0" r="2.5" fill="#FF8B20"/>
            <circle cx="12" cy="-8" r="2.5" fill="#333"/>
            <circle cx="12" cy="8" r="2.5" fill="#FF8B20" opacity="0.7"/>
            
            <!-- Connection lines -->
            <line x1="0" y1="0" x2="12" y2="-8" stroke="#FF8B20" stroke-width="1" opacity="0.6"/>
            <line x1="0" y1="0" x2="12" y2="8" stroke="#FF8B20" stroke-width="1" opacity="0.6"/>
            <line x1="12" y1="-8" x2="12" y2="8" stroke="#333" stroke-width="1" opacity="0.4"/>
        </g>
    </svg>
</div>