import React from 'react';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import CardMedia from '@mui/material/CardMedia';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import CardActionArea from '@mui/material/CardActionArea';

export default function WelcomeCard({ handleStartOnboarding }) {
  return (
    <Card sx={{ maxWidth: 345 }}>
      <CardActionArea>
    
        <CardContent>
          <Typography gutterBottom variant="h5" component="div">
            Welcome to Onboarding
          </Typography>
          <Typography variant="body2" color="text.primary">
            Start your onboarding process by providing your account details.
          </Typography>
        </CardContent>
      </CardActionArea>
      <Button
        size="large"
        color="primary"
        onClick={handleStartOnboarding}
        sx={{ margin: '16px' }}
      >
        Start Onboarding
      </Button>
    </Card>
  );
}
