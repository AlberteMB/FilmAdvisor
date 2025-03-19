interface PanelProps {
  title: string;
  // children can contain any React component
  children: React.ReactNode;
  isOpen: boolean;
  onToggle: () => void;
}

export { PanelProps };