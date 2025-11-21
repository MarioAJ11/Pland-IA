'use client';

interface BatPixelProps {
  size?: number;
  className?: string;
}

export default function BatPixel({ size = 4, className = '' }: BatPixelProps) {
  const batStyle = {
    width: '1px',
    height: '1px',
    transform: `scale(${size})`,
    position: 'relative' as const,
    boxShadow: `
      33px 6px #54556b, 34px 6px #54556b, 35px 6px #54556b, 36px 6px #54556b,
      33px 7px #54556b, 34px 7px #54556b, 35px 7px #202020, 36px 7px #202020, 37px 7px #54556b, 38px 7px #54556b,
      33px 8px #54556b, 34px 8px #54556b, 35px 8px #202020, 36px 8px #202020, 37px 8px #54556b, 38px 8px #54556b,
      35px 9px #54556b, 36px 9px #202020, 37px 9px #202020, 38px 9px #202020, 39px 9px #202020, 40px 9px #54556b,
      36px 10px #54556b, 37px 10px #202020, 38px 10px #202020, 39px 10px #202020, 40px 10px #202020, 41px 10px #202020, 42px 10px #202020, 43px 10px #202020, 44px 10px #54556b,
      36px 11px #54556b, 37px 11px #202020, 38px 11px #202020, 39px 11px #202020, 40px 11px #202020, 41px 11px #202020, 42px 11px #202020, 43px 11px #202020, 44px 11px #54556b
    `.replace(/\s+/g, ' ').trim()
  };

  return (
    <div className={`relative ${className}`}>
      <div style={batStyle}></div>
    </div>
  );
}
