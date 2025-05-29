<%@ page contentType="text/html; charset=UTF-8" %>
<!-- =================================== -->
<!-- モバイル対応共通ヘッダーロゴコンポーネント -->
<!-- =================================== -->
<div class="header-logo">
    <svg viewBox="0 0 800 120" xmlns="http://www.w3.org/2000/svg" 
         class="logo-svg"
         preserveAspectRatio="xMidYMid meet">
        <!-- Background circles -->
        <circle cx="40" cy="60" r="10" fill="#FF8B20" opacity="0.7" class="bg-circle"/>
        <circle cx="750" cy="30" r="8" fill="#333" opacity="0.8" class="bg-circle"/>
        <circle cx="720" cy="90" r="6" fill="#FF8B20" opacity="0.5" class="bg-circle"/>
        
        <!-- Main JB icon container -->
        <g transform="translate(15, 15)" class="icon-group">
            <!-- Outer frame -->
            <path d="M0 15 L0 0 L30 0 L45 15 L45 60 L60 75 L60 105 L30 105 L15 90 L15 75 L0 60 Z" 
                  fill="none" stroke="#FF8B20" stroke-width="6" stroke-linejoin="round" class="outer-frame"/>
            
            <!-- Inner frame -->
            <path d="M10 25 L10 15 L25 15 L35 25 L35 55 L45 65 L45 90 L25 90 L15 80 L15 65 L10 60 Z" 
                  fill="#FF8B20" opacity="0.2" class="inner-frame"/>
            
            <!-- J letter -->
            <path d="M18 30 L18 65 Q18 75 25 75 Q32 75 32 65 L32 58" 
                  fill="none" stroke="white" stroke-width="4" stroke-linecap="round" class="j-letter"/>
            
            <!-- B letter -->
            <path d="M40 30 L40 80 M40 30 L50 30 Q54 30 54 38 Q54 45 50 45 L40 45 M40 45 L52 45 Q56 45 56 53 Q56 60 52 60 L40 60" 
                  fill="none" stroke="white" stroke-width="3" stroke-linecap="round" class="b-letter"/>
            
            <!-- Decorative arc -->
            <path d="M8 85 Q30 98 52 85" fill="none" stroke="#B8860B" stroke-width="4" opacity="0.8" class="decorative-arc"/>
        </g>
        
        <!-- jobridge text -->
        <g transform="translate(140, 60)" fill="#333" font-family="Arial, sans-serif" font-weight="bold" class="jobridge-text">
            <!-- jo -->
            <text x="0" y="15" font-size="48" fill="#333" class="jo-text">jo</text>
            <!-- bridge with stylized 'r' -->
            <text x="50" y="15" font-size="48" fill="#FF8B20" class="bridge-b">b</text>
            <text x="82" y="15" font-size="48" fill="#FF8B20" class="bridge-r">r</text>
            <text x="110" y="15" font-size="48" fill="#333" class="bridge-idge">idge</text>
        </g>
        
        <!-- chat text -->
        <g transform="translate(380, 60)" fill="#333" font-family="Arial, sans-serif" font-weight="bold" class="chat-text">
            <!-- ch -->
            <text x="10" y="15" font-size="48" fill="#333" class="chat-ch">ch</text>
            <!-- at with stylized 'a' -->
            <text x="80" y="15" font-size="48" fill="#FF8B20" class="chat-a">a</text>
            <text x="112" y="15" font-size="48" fill="#333" class="chat-t">t</text>
        </g>
        
        <!-- Chat bubble decorations -->
        <g transform="translate(550, 20)" class="chat-bubble-group">
            <!-- Large bubble -->
            <ellipse cx="25" cy="25" rx="28" ry="20" fill="#FF8B20" opacity="0.3" class="large-bubble"/>
            <ellipse cx="25" cy="25" rx="28" ry="20" fill="none" stroke="#FF8B20" stroke-width="2" class="large-bubble-outline"/>
            
            <!-- Small bubble dots -->
            <circle cx="8" cy="40" r="3" fill="#FF8B20" class="small-bubble"/>
            <circle cx="16" cy="44" r="2" fill="#FF8B20" opacity="0.7" class="small-bubble"/>
            <circle cx="4" cy="48" r="1.5" fill="#FF8B20" opacity="0.5" class="small-bubble"/>
        </g>
        
        <!-- Connection lines -->
        <g stroke="#FF8B20" stroke-width="1.5" opacity="0.4" class="connection-lines">
            <path d="M120 50 Q200 35 280 50" fill="none" class="line1"/>
            <path d="M360 75 Q420 85 480 75" fill="none" class="line2"/>
        </g>
        
        <!-- Additional decorative elements -->
        <g transform="translate(680, 60)" class="network-nodes">
            <!-- Network nodes -->
            <circle cx="0" cy="0" r="2.5" fill="#FF8B20" class="node"/>
            <circle cx="12" cy="-8" r="2.5" fill="#333" class="node"/>
            <circle cx="12" cy="8" r="2.5" fill="#FF8B20" opacity="0.7" class="node"/>
            
            <!-- Connection lines -->
            <line x1="0" y1="0" x2="12" y2="-8" stroke="#FF8B20" stroke-width="1" opacity="0.6" class="node-line"/>
            <line x1="0" y1="0" x2="12" y2="8" stroke="#FF8B20" stroke-width="1" opacity="0.6" class="node-line"/>
            <line x1="12" y1="-8" x2="12" y2="8" stroke="#333" stroke-width="1" opacity="0.4" class="node-line"/>
        </g>
    </svg>
</div>

<style>
/* モバイル対応ヘッダーロゴスタイル */
.header-logo {
    text-align: center;
    padding: 10px 5px;
    background: linear-gradient(135deg, #ffffff 0%, #fefefe 100%);
    border-bottom: 3px solid #f0f0f0;
    margin-bottom: 10px;
    border-radius: 12px 12px 0 0;
    overflow: hidden;
}

.logo-svg {
    max-width: 100%;
    height: auto;
    filter: drop-shadow(0 4px 8px rgba(255, 139, 32, 0.15));
    transition: all 0.3s ease;
}

/* タブレット対応 (768px以下) */
@media (max-width: 768px) {
    .header-logo {
        padding: 8px 3px;
        border-radius: 8px 8px 0 0;
    }
    
    .logo-svg {
        max-width: 350px;
        height: auto;
        min-height: 60px;
    }
    
    /* テキストサイズを調整 */
    .jobridge-text text,
    .chat-text text {
        font-size: 56px;
    }
    
    /* 小さい装飾要素を少し大きく */
    .network-nodes .node {
        r: 3;
    }
    
    .small-bubble {
        r: 3.5;
    }
}

/* スマートフォン対応 (480px以下) */
@media (max-width: 480px) {
    .header-logo {
        padding: 5px 2px;
        margin-bottom: 8px;
        border-radius: 6px 6px 0 0;
    }
    
    .logo-svg {
        max-width: 300px;
        height: auto;
        min-height: 50px;
    }
    
    /* テキストサイズをさらに調整 */
    .jobridge-text text,
    .chat-text text {
        font-size: 48px;
    }
    
    /* アイコンのストローク幅を調整 */
    .outer-frame {
        stroke-width: 5;
    }
    
    .j-letter {
        stroke-width: 3.5;
    }
    
    .b-letter {
        stroke-width: 2.5;
    }
    
    .decorative-arc {
        stroke-width: 3;
    }
    
    /* 背景の円を少し小さく */
    .bg-circle {
        r: 8;
    }
    
    /* チャットバブルのサイズ調整 */
    .large-bubble,
    .large-bubble-outline {
        rx: 22;
        ry: 16;
    }
    
    .small-bubble {
        r: 2.5;
    }
}

/* 極小画面対応 (320px以下) */
@media (max-width: 320px) {
    .header-logo {
        padding: 3px 1px;
        margin-bottom: 5px;
    }
    
    .logo-svg {
        max-width: 280px;
        min-height: 40px;
    }
    
    /* テキストサイズを最小に */
    .jobridge-text text,
    .chat-text text {
        font-size: 40px;
    }
    
    /* 装飾要素を簡素化 */
    .connection-lines {
        opacity: 0.2;
    }
    
    .network-nodes {
        opacity: 0.8;
    }
    
    /* 背景円をさらに小さく */
    .bg-circle {
        r: 6;
    }
}

/* 高解像度ディスプレイ対応 */
@media (-webkit-min-device-pixel-ratio: 2), (min-resolution: 192dpi) {
    .logo-svg {
        image-rendering: -webkit-optimize-contrast;
        image-rendering: crisp-edges;
    }
}

/* ダークモード対応 */
@media (prefers-color-scheme: dark) {
    .header-logo {
        background: linear-gradient(135deg, #2a2a2a 0%, #1f1f1f 100%);
        border-bottom-color: #404040;
    }
    
    .jobridge-text .jo-text,
    .jobridge-text .bridge-idge,
    .chat-text .chat-ch,
    .chat-text .chat-t {
        fill: #ffffff;
    }
}

/* 縦向き・横向き対応 */
@media (orientation: landscape) and (max-height: 500px) {
    .header-logo {
        padding: 2px;
    }
    
    .logo-svg {
        max-height: 40px;
        width: auto;
    }
}

/* タッチデバイス用のホバー効果無効化 */
@media (hover: none) and (pointer: coarse) {
    .logo-svg {
        transition: none;
    }
}

/* アクセシビリティ：動きを減らす設定 */
@media (prefers-reduced-motion: reduce) {
    .logo-svg,
    .header-logo {
        transition: none;
    }
}

/* 印刷時の調整 */
@media print {
    .header-logo {
        background: none;
        border: none;
        padding: 0;
        margin: 0;
    }
    
    .logo-svg {
        max-width: 200px;
        filter: none;
    }
}
</style>