import React from 'react';
import { Container, Typography, Box } from '@mui/material';
import dynamic from 'next/dynamic';

// Import dynamique du composant RailwayMap pour éviter les erreurs SSR avec Leaflet
const RailwayMap = dynamic(() => import('../components/RailwayMap'), {
  ssr: false,
  loading: () => <Typography>Chargement de la carte...</Typography>
});

const RailwayMapPage: React.FC = () => {
  return (
    <Container maxWidth="xl">
      <Box sx={{ my: 4 }}>
        <Typography variant="h4" component="h1" gutterBottom>
          Carte des Lignes Ferroviaires
        </Typography>
        <RailwayMap />
      </Box>
    </Container>
  );
};

export default RailwayMapPage; 